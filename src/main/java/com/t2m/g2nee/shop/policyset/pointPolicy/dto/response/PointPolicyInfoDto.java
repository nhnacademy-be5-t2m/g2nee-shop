package com.t2m.g2nee.shop.policyset.pointPolicy.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;
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
    private BigDecimal amount;
    private Boolean isActivated;
    private LocalDateTime changedDate;
}
