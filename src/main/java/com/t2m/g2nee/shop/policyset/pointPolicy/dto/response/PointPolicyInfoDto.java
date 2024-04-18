package com.t2m.g2nee.shop.policyset.pointPolicy.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 포인트 정책에 대한 정보를 반환하는 객체입니다.
 *
 * @author : 김수빈
 * @since : 1.0
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class PointPolicyInfoDto {
    private Long pointPolicyId;
    private String policyName;
    private String policyType;
    private String amount;
    private Boolean isActivated;
    private String changedDate;
}
