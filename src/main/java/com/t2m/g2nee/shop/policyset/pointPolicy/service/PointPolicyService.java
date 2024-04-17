package com.t2m.g2nee.shop.policyset.pointPolicy.service;

import com.t2m.g2nee.shop.pageUtils.PageResponse;
import com.t2m.g2nee.shop.policyset.pointPolicy.dto.request.PointPolicySaveDto;
import com.t2m.g2nee.shop.policyset.pointPolicy.dto.response.PointPolicyInfoDto;

public interface PointPolicyService {

    PointPolicyInfoDto saveDeliveryPolicy(PointPolicySaveDto request);

    PointPolicyInfoDto getDeliveryPolicy();

    PageResponse<PointPolicyInfoDto> getAllDeliveryPolicy(int page);
}
