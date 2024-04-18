package com.t2m.g2nee.shop.policyset.deliveryPolicy.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class DeliveryPolicyInfoDto {
    private Long deliveryPolicyId;

    private int deliveryFee;
    private int freeDeliveryStandard;

    private Boolean isActivated;
    private String changedDate;
}
