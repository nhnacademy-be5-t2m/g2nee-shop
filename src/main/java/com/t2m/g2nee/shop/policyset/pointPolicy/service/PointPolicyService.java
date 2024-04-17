package com.t2m.g2nee.shop.policyset.pointPolicy.service;

import com.t2m.g2nee.shop.pageUtils.PageResponse;
import com.t2m.g2nee.shop.policyset.pointPolicy.dto.request.PointPolicySaveDto;
import com.t2m.g2nee.shop.policyset.pointPolicy.dto.response.PointPolicyInfoDto;

public interface PointPolicyService {

    PointPolicyInfoDto savePointPolicy(PointPolicySaveDto request);

    PointPolicyInfoDto updatePointPolicy(Long pointPolicyId, PointPolicySaveDto request);

    boolean softDeletePointPolicy(Long pointPolicyId);

    PointPolicyInfoDto getPointPolicy(Long pointPolicyId);

    PageResponse<PointPolicyInfoDto> getAllPointPolicy(int page);
}
