package com.t2m.g2nee.shop.policyset.pointPolicy.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
