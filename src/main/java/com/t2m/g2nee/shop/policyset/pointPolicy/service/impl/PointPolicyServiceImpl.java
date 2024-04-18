package com.t2m.g2nee.shop.policyset.pointPolicy.service.impl;

import com.t2m.g2nee.shop.exception.AlreadyExistException;
import com.t2m.g2nee.shop.exception.NotFoundException;
import com.t2m.g2nee.shop.pageUtils.PageResponse;
import com.t2m.g2nee.shop.policyset.pointPolicy.domain.PointPolicy;
import com.t2m.g2nee.shop.policyset.pointPolicy.dto.request.PointPolicySaveDto;
import com.t2m.g2nee.shop.policyset.pointPolicy.dto.response.PointPolicyInfoDto;
import com.t2m.g2nee.shop.policyset.pointPolicy.repository.PointPolicyRepository;
import com.t2m.g2nee.shop.policyset.pointPolicy.service.PointPolicyService;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PointPolicyServiceImpl implements PointPolicyService {

    private static int maxPageButtons = 5;
    private final PointPolicyRepository pointPolicyRepository;

    public PointPolicyServiceImpl(PointPolicyRepository pointPolicyRepository) {
        this.pointPolicyRepository = pointPolicyRepository;
    }

    @Override
    public PointPolicyInfoDto savePointPolicy(PointPolicySaveDto request) {
        if (pointPolicyRepository.existsByPolicyNameAndIsActivated(request.getPolicyName(), true)) {
            throw new AlreadyExistException("이미 존재하는 정책입니다.");
        } else {
            return convertToPointPolicyInfoDto(pointPolicyRepository.save(convertToPointPolicy(request)));
        }
    }

    @Override
    public PointPolicyInfoDto updatePointPolicy(Long pointPolicyId, PointPolicySaveDto request) {
        if (pointPolicyRepository.existsById(pointPolicyId)) {
            //이전 정책을 비활성화
            pointPolicyRepository.softDelete(pointPolicyId);
            //새롭게 정책 저장
            return convertToPointPolicyInfoDto(pointPolicyRepository.save(convertToPointPolicy(request)));
        } else {
            throw new NotFoundException("존재하지 않는 정책입니다.");
        }
    }

    @Override
    public boolean softDeletePointPolicy(Long pointPolicyId) {
        if (pointPolicyRepository.existsById(pointPolicyId)) {
            pointPolicyRepository.softDelete(pointPolicyId);
            return convertToPointPolicyInfoDto(pointPolicyRepository.findById(pointPolicyId)
                    .orElseThrow(() -> new NotFoundException("배송비 정책이 존재하지 않습니다"))).getIsActivated();
        } else {
            throw new NotFoundException("삭제할 포인트 정책이 존재하지 않습니다.");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public PointPolicyInfoDto getPointPolicy(Long pointPolicyId) {
        return convertToPointPolicyInfoDto(pointPolicyRepository.findById(pointPolicyId)
                .orElseThrow(() -> new NotFoundException("배송비 정책이 존재하지 않습니다")));
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<PointPolicyInfoDto> getAllPointPolicy(int page) {
        Page<PointPolicy> deliveryPolicies = pointPolicyRepository.findAll(
                PageRequest.of(page - 1, 10, Sort.by("isActivated").descending())
        );

        List<PointPolicyInfoDto> deliveryPolicyInfoDtoList = deliveryPolicies
                .stream().map(this::convertToPointPolicyInfoDto)
                .collect(Collectors.toList());

        int startPage = (int) Math.max(1, deliveryPolicies.getNumber() - Math.floor((double) maxPageButtons / 2));
        int endPage = Math.min(startPage + maxPageButtons - 1, deliveryPolicies.getTotalPages());

        if (endPage - startPage + 1 < maxPageButtons) {
            startPage = Math.max(1, endPage - maxPageButtons + 1);
        }


        return PageResponse.<PointPolicyInfoDto>builder()
                .data(deliveryPolicyInfoDtoList)
                .currentPage(page)
                .totalPage(deliveryPolicies.getTotalPages())
                .startPage(startPage)
                .endPage(endPage)
                .totalElements(deliveryPolicies.getTotalElements())
                .build();
    }

    private PointPolicyInfoDto convertToPointPolicyInfoDto(PointPolicy pointPolicy) {
        return new PointPolicyInfoDto(pointPolicy.getPointPolicyId(), pointPolicy.getPolicyName(),
                pointPolicy.getPolicyType().getName(), pointPolicy.getAmount().toString(), pointPolicy.getIsActivated(),
                pointPolicy.getChangedDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
    }

    private PointPolicy convertToPointPolicy(PointPolicySaveDto pointPolicySaveDto) {
        return new PointPolicy(pointPolicySaveDto.getPolicyName(), pointPolicySaveDto.getPolicyType(),
                pointPolicySaveDto.getAmount());
    }
}
