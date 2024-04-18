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

/**
 * PointPolicyService의 구현체입니다.
 *
 * @author : 김수빈
 * @since : 1.0
 */
@Service
@Transactional
public class PointPolicyServiceImpl implements PointPolicyService {

    private static int maxPageButtons = 5;
    private final PointPolicyRepository pointPolicyRepository;

    public PointPolicyServiceImpl(PointPolicyRepository pointPolicyRepository) {
        this.pointPolicyRepository = pointPolicyRepository;
    }

    @Override
    public PointPolicyInfoDto savePointPolicy(PointPolicySaveDto request) {
        //활성화면서 이름으로 존재하는지 확인
        if (pointPolicyRepository.existsByPolicyNameAndIsActivated(request.getPolicyName(), true)) {
            //존재하면 예외를 던짐
            throw new AlreadyExistException("이미 존재하는 정책입니다.");
        } else {
            //존재하지 않으면 저장
            return convertToPointPolicyInfoDto(pointPolicyRepository.save(convertToPointPolicy(request)));
        }
    }

    @Override
    public PointPolicyInfoDto updatePointPolicy(Long pointPolicyId, PointPolicySaveDto request) {
        //정책이 존재하는지 확인
        if (pointPolicyRepository.existsById(pointPolicyId)) {
            //이전 정책을 비활성화: 기록으로 남기기 위해 새롭게 만드는 방법 선택
            pointPolicyRepository.softDelete(pointPolicyId);
            //새롭게 정책 저장
            return convertToPointPolicyInfoDto(pointPolicyRepository.save(convertToPointPolicy(request)));
        } else {
            //없을 경우 예외
            throw new NotFoundException("존재하지 않는 정책입니다.");
        }
    }

    @Override
    public boolean softDeletePointPolicy(Long pointPolicyId) {
        //활성화 상태로 존재하는지 확인
        if (pointPolicyRepository.getExistsByPointPolicyIdAndIsActivated(pointPolicyId, true)) {
            //존재하는 경우 비활성화
            pointPolicyRepository.softDelete(pointPolicyId);
            return false;
        } else {
            throw new NotFoundException("삭제할 포인트 정책이 존재하지 않습니다.");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public PointPolicyInfoDto getPointPolicy(Long pointPolicyId) {
        //한 정책을 가져와 존재하지 않으면 예외 발생
        return convertToPointPolicyInfoDto(pointPolicyRepository.findById(pointPolicyId)
                .orElseThrow(() -> new NotFoundException("포인트 정책이 존재하지 않습니다")));
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<PointPolicyInfoDto> getAllPointPolicy(int page) {
        //활성화된 정책을 먼저 보여줌
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
        String amount = pointPolicy.getAmount().toString();
        if (pointPolicy.getPolicyType().equals(PointPolicy.PolicyType.AMOUNT)) {
            //금액의 경우 소수점을 떼고 보여줌
            int dot = amount.indexOf(".");
            if (dot != -1) {
                amount = amount.substring(0, dot);
            }
        }

        return new PointPolicyInfoDto(pointPolicy.getPointPolicyId(), pointPolicy.getPolicyName(),
                pointPolicy.getPolicyType().getName(), amount, pointPolicy.getIsActivated(),
                pointPolicy.getChangedDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
    }

    private PointPolicy convertToPointPolicy(PointPolicySaveDto pointPolicySaveDto) {
        return new PointPolicy(pointPolicySaveDto.getPolicyName(), pointPolicySaveDto.getPolicyType(),
                pointPolicySaveDto.getAmount());
    }
}
