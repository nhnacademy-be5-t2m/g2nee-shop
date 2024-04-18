package com.t2m.g2nee.shop.policyset.deliveryPolicy.service;

import com.t2m.g2nee.shop.pageUtils.PageResponse;
import com.t2m.g2nee.shop.policyset.deliveryPolicy.dto.request.DeliveryPolicySaveDto;
import com.t2m.g2nee.shop.policyset.deliveryPolicy.dto.response.DeliveryPolicyInfoDto;

/**
 * 배송비 정책의 저장, 조회를 위한 서비스입니다.
 *
 * @author : 김수빈
 * @since : 1.0
 */
public interface DeliveryPolicyService {

    /**
     * 배송비 정책을 저장합니다.
     * @param request 배송비 정책을 저장하기 위한 객체
     * @return DeliveryPolicyInfoDto
     */
    DeliveryPolicyInfoDto saveDeliveryPolicy(DeliveryPolicySaveDto request);

    /**
     * 배송비 정책 중 현재 반영되고 있는 정책을 반환합니다.
     * @return DeliveryPolicyInfoDto
     */
    DeliveryPolicyInfoDto getDeliveryPolicy();

    /**
     * 모든 배송비 정책을 반환합니다. 활성중인 배송비가 먼저 반환됩니다.
     * @param page 현재 페이지
     * @return PageResponse<DeliveryPolicyInfoDto>
     */
    PageResponse<DeliveryPolicyInfoDto> getAllDeliveryPolicy(int page);
}
