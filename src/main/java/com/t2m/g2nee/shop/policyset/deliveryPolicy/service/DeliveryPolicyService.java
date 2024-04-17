package com.t2m.g2nee.shop.policyset.deliveryPolicy.service;

import com.t2m.g2nee.shop.pageUtils.PageResponse;
import com.t2m.g2nee.shop.policyset.deliveryPolicy.dto.request.DeliveryPolicySaveDto;
import com.t2m.g2nee.shop.policyset.deliveryPolicy.dto.response.DeliveryPolicyInfoDto;

public interface DeliveryPolicyService {

    DeliveryPolicyInfoDto saveDeliveryPolicy(DeliveryPolicySaveDto request);

    DeliveryPolicyInfoDto getDeliveryPolicy();

    PageResponse<DeliveryPolicyInfoDto> getAllDeliveryPolicy(int page);
}
