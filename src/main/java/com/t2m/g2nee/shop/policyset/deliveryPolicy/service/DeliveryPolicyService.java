package com.t2m.g2nee.shop.policyset.deliveryPolicy.service;

import com.t2m.g2nee.shop.pageUtils.PageResponse;
import com.t2m.g2nee.shop.policyset.deliveryPolicy.dto.request.DeliveryPolicySaveDto;
import com.t2m.g2nee.shop.policyset.deliveryPolicy.dto.response.DeliveryPolicyInfoDto;

public interface DeliveryPolicyService {

    /**
     * @param request
     * @return
     */
    DeliveryPolicyInfoDto saveDeliveryPolicy(DeliveryPolicySaveDto request);

    /**
     * 최근 배송비 정책 불러옴
     *
     * @return
     */
    DeliveryPolicyInfoDto getDeliveryPolicy();

    /**
     * @param page
     * @return
     */
    PageResponse<DeliveryPolicyInfoDto> getAllDeliveryPolicy(int page);
}
