package com.t2m.g2nee.shop.policyset.deliveryPolicy.service.impl;

import com.t2m.g2nee.shop.exception.NotFoundException;
import com.t2m.g2nee.shop.pageUtils.PageResponse;
import com.t2m.g2nee.shop.policyset.deliveryPolicy.domain.DeliveryPolicy;
import com.t2m.g2nee.shop.policyset.deliveryPolicy.dto.request.DeliveryPolicySaveDto;
import com.t2m.g2nee.shop.policyset.deliveryPolicy.dto.response.DeliveryPolicyInfoDto;
import com.t2m.g2nee.shop.policyset.deliveryPolicy.repository.DeliveryPolicyRepository;
import com.t2m.g2nee.shop.policyset.deliveryPolicy.service.DeliveryPolicyService;
import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DeliveryPolicyServiceImpl implements DeliveryPolicyService {

    private static int maxPageButtons = 5;
    private final DeliveryPolicyRepository deliveryPolicyRepository;

    public DeliveryPolicyServiceImpl(DeliveryPolicyRepository deliveryPolicyRepository) {
        this.deliveryPolicyRepository = deliveryPolicyRepository;
    }

    @Override
    public DeliveryPolicyInfoDto saveDeliveryPolicy(DeliveryPolicySaveDto request) {
        if (deliveryPolicyRepository.count() == 0) {//처음으로 저장하는 경우
            return convertToDeliveryPolicyInfoDto(deliveryPolicyRepository.save(convertToDeliveryPolicy(request)));
        } else {//변경해야할 경우
            //이전 정책과 변경사항 비교
            DeliveryPolicy oldPolicy = deliveryPolicyRepository.findFirstByIsActivatedOrderByChangedDateDesc(true)
                    .orElseThrow(() -> new NotFoundException("이전 정책이 없습니다."));
            if ((oldPolicy.getDeliveryFee() != BigDecimal.valueOf(request.getDeliveryFee())) ||
                    (oldPolicy.getFreeDeliveryStandard() != BigDecimal.valueOf(request.getFreeDeliveryStandard()))) {
                //이전의 정책을 비활성화로 변경
                deliveryPolicyRepository.softDelete();
                //새로운 정책 저장
                return convertToDeliveryPolicyInfoDto(deliveryPolicyRepository.save(convertToDeliveryPolicy(request)));
            }
            //변경사항이 없을 경우, 이전 정책을 그대로 리턴
            return convertToDeliveryPolicyInfoDto(oldPolicy);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public DeliveryPolicyInfoDto getDeliveryPolicy() {
        return convertToDeliveryPolicyInfoDto(
                deliveryPolicyRepository.findFirstByIsActivatedOrderByChangedDateDesc(true)
                        .orElseThrow(() -> new NotFoundException("배송비 정책이 존재하지 않습니다")));
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<DeliveryPolicyInfoDto> getAllDeliveryPolicy(int page) {
        Page<DeliveryPolicy> deliveryPolicies = deliveryPolicyRepository.findAll(
                PageRequest.of(page - 1, 10, Sort.by("isActivated").descending()
                        .and(Sort.by("changedDate")))
        );

        List<DeliveryPolicyInfoDto> deliveryPolicyInfoDtoList = deliveryPolicies
                .stream().map(this::convertToDeliveryPolicyInfoDto)
                .collect(Collectors.toList());

        int startPage = (int) Math.max(1, deliveryPolicies.getNumber() - Math.floor((double) maxPageButtons / 2));
        int endPage = Math.min(startPage + maxPageButtons - 1, deliveryPolicies.getTotalPages());

        if (endPage - startPage + 1 < maxPageButtons) {
            startPage = Math.max(1, endPage - maxPageButtons + 1);
        }


        return PageResponse.<DeliveryPolicyInfoDto>builder()
                .data(deliveryPolicyInfoDtoList)
                .currentPage(page)
                .totalPage(deliveryPolicies.getTotalPages())
                .startPage(startPage)
                .endPage(endPage)
                .totalElements(deliveryPolicies.getTotalElements())
                .build();
    }

    private DeliveryPolicyInfoDto convertToDeliveryPolicyInfoDto(DeliveryPolicy deliveryPolicy) {
        return new DeliveryPolicyInfoDto(deliveryPolicy.getDeliveryPolicyId(),
                deliveryPolicy.getDeliveryFee().intValue(),
                deliveryPolicy.getFreeDeliveryStandard().intValue(), deliveryPolicy.getIsActivated(),
                deliveryPolicy.getChangedDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
    }

    private DeliveryPolicy convertToDeliveryPolicy(DeliveryPolicySaveDto deliveryPolicySaveDto) {
        return new DeliveryPolicy(BigDecimal.valueOf(deliveryPolicySaveDto.getDeliveryFee()),
                BigDecimal.valueOf(deliveryPolicySaveDto.getFreeDeliveryStandard()));
    }
}
