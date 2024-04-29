package com.t2m.g2nee.shop.policyset.deliverypolicy.service.impl;

import com.t2m.g2nee.shop.exception.NotFoundException;
import com.t2m.g2nee.shop.pageUtils.PageResponse;
import com.t2m.g2nee.shop.policyset.deliverypolicy.domain.DeliveryPolicy;
import com.t2m.g2nee.shop.policyset.deliverypolicy.dto.request.DeliveryPolicySaveDto;
import com.t2m.g2nee.shop.policyset.deliverypolicy.dto.response.DeliveryPolicyInfoDto;
import com.t2m.g2nee.shop.policyset.deliverypolicy.repository.DeliveryPolicyRepository;
import com.t2m.g2nee.shop.policyset.deliverypolicy.service.DeliveryPolicyService;
import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * DeliveryPolicyService의 구현체입니다.
 *
 * @author : 김수빈
 * @since : 1.0
 */
@Service
@Transactional
public class DeliveryPolicyServiceImpl implements DeliveryPolicyService {

    private static final int MAXPAGEBUTTONS = 5;
    private final DeliveryPolicyRepository deliveryPolicyRepository;

    /**
     * DeliveryPolicyServiceImpl의 생성자입니다.
     * @param deliveryPolicyRepository 배송비 정책 레포지토리
     */
    public DeliveryPolicyServiceImpl(DeliveryPolicyRepository deliveryPolicyRepository) {
        this.deliveryPolicyRepository = deliveryPolicyRepository;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public DeliveryPolicyInfoDto saveDeliveryPolicy(DeliveryPolicySaveDto request) {
        if (deliveryPolicyRepository.count() == 0) {//처음으로 저장하는 경우
            return convertToDeliveryPolicyInfoDto(deliveryPolicyRepository.save(convertToDeliveryPolicy(request)));
        } else {//변경해야할 경우: 어짜피 배송비 정책은 1개
            //이전 정책과 변경사항 비교
            DeliveryPolicy oldPolicy = deliveryPolicyRepository.findFirstByIsActivatedOrderByChangedDateDesc(true)
                    .orElse(null);
            if (Objects.nonNull(oldPolicy) &&
                    ((oldPolicy.getDeliveryFee() != BigDecimal.valueOf(request.getDeliveryFee())) ||
                            (oldPolicy.getFreeDeliveryStandard() !=
                                    BigDecimal.valueOf(request.getFreeDeliveryStandard())))) {
                //이전의 정책을 비활성화로 변경
                deliveryPolicyRepository.softDelete();
                //새로운 정책 저장
                return convertToDeliveryPolicyInfoDto(deliveryPolicyRepository.save(convertToDeliveryPolicy(request)));
            }
            //변경사항이 없을 경우, 이전 정책을 그대로 리턴
            return convertToDeliveryPolicyInfoDto(oldPolicy);
        }
    }

    /**
     * {@inheritDoc}
     * @throws NotFoundException 유효한 배송비 정책이 존재하지 않을 때
     */
    @Override
    @Transactional(readOnly = true)
    public DeliveryPolicyInfoDto getDeliveryPolicy() {
        return convertToDeliveryPolicyInfoDto(
                deliveryPolicyRepository.findFirstByIsActivatedOrderByChangedDateDesc(true)
                        .orElseThrow(() -> new NotFoundException("배송비 정책이 존재하지 않습니다")));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public PageResponse<DeliveryPolicyInfoDto> getAllDeliveryPolicy(int page) {
        //활성화된 배송비 정책이 먼저 보이고, 나머지는 변경 날짜 순서로 정렬
        Page<DeliveryPolicy> deliveryPolicies = deliveryPolicyRepository.findAll(
                PageRequest.of(page - 1, 10, Sort.by("isActivated").descending()
                        .and(Sort.by("changedDate")))
        );

        List<DeliveryPolicyInfoDto> deliveryPolicyInfoDtoList = deliveryPolicies
                .stream().map(this::convertToDeliveryPolicyInfoDto)
                .collect(Collectors.toList());

        int startPage = (int) Math.max(1, deliveryPolicies.getNumber() - Math.floor((double) MAXPAGEBUTTONS / 2));
        int endPage = Math.min(startPage + MAXPAGEBUTTONS - 1, deliveryPolicies.getTotalPages());

        if (endPage - startPage + 1 < MAXPAGEBUTTONS) {
            startPage = Math.max(1, endPage - MAXPAGEBUTTONS + 1);
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

    /**
     * DeliveryPolicy객체를 DeliveryPolicyInfoDto로 변경
     *
     * @param deliveryPolicy 배송비 정책 객체
     * @return DeliveryPolicyInfoDto
     */
    private DeliveryPolicyInfoDto convertToDeliveryPolicyInfoDto(DeliveryPolicy deliveryPolicy) {
        return new DeliveryPolicyInfoDto(deliveryPolicy.getDeliveryPolicyId(),
                deliveryPolicy.getDeliveryFee().intValue(),
                deliveryPolicy.getFreeDeliveryStandard().intValue(), deliveryPolicy.getIsActivated(),
                deliveryPolicy.getChangedDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
    }

    /**
     * DeliveryPolicySaveDto객체를 DeliveryPolicy로 변경
     *
     * @param deliveryPolicySaveDto 배송비 저장 객체
     * @return DeliveryPolicy
     */
    private DeliveryPolicy convertToDeliveryPolicy(DeliveryPolicySaveDto deliveryPolicySaveDto) {
        return new DeliveryPolicy(BigDecimal.valueOf(deliveryPolicySaveDto.getDeliveryFee()),
                BigDecimal.valueOf(deliveryPolicySaveDto.getFreeDeliveryStandard()));
    }
}
