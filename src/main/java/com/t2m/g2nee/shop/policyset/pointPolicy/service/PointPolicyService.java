package com.t2m.g2nee.shop.policyset.pointPolicy.service;

import com.t2m.g2nee.shop.pageUtils.PageResponse;
import com.t2m.g2nee.shop.policyset.pointPolicy.dto.request.PointPolicySaveDto;
import com.t2m.g2nee.shop.policyset.pointPolicy.dto.response.PointPolicyInfoDto;

/**
 * 포인트 정책 서비스를 위한 인터페이스입니다.
 */
public interface PointPolicyService {

    /**
     * 포인트 정책을 저장합니다.
     *
     * @param request 저장할 객체
     * @return PointPolicyInfoDto
     */
    PointPolicyInfoDto savePointPolicy(PointPolicySaveDto request);

    /**
     * 포인트 정책을 수정합니다.
     * @param pointPolicyId 포인트 정책 id
     * @param request 저장할 객체
     * @return PointPolicyInfoDto
     */
    PointPolicyInfoDto updatePointPolicy(Long pointPolicyId, PointPolicySaveDto request);

    /**
     * 포인트 정책을 삭제 합니다.
     * @param pointPolicyId 포인트 정책 id
     * @return boolean
     */
    boolean softDeletePointPolicy(Long pointPolicyId);

    /**
     * 특정 포인트 정책을 가져옵니다.
     * @param pointPolicyId 포인트 정책 id
     * @return PointPolicyInfoDto
     */
    PointPolicyInfoDto getPointPolicy(Long pointPolicyId);

    /**
     * 모든 포인트 정책을 페이징 처리하여 반환합니다.
     * @param page 현재 페이지
     * @return PageResponse<PointPolicyInfoDto>
     */
    PageResponse<PointPolicyInfoDto> getAllPointPolicy(int page);
}
