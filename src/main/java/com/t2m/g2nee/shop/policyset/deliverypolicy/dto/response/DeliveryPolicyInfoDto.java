package com.t2m.g2nee.shop.policyset.deliverypolicy.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 배송비 정책을 보여주기 위한 객체입니다.
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class DeliveryPolicyInfoDto {
    /**
     * 배송비 정책 id
     */
    private Long deliveryPolicyId;

    /**
     * 배송비
     */
    private int deliveryFee;
    /**
     * 무료 배송 기준
     */
    private int freeDeliveryStandard;

    /**
     * 정책 활성화 여부
     */
    private Boolean isActivated;
    /**
     * 정책 변경 날짜
     */
    private String changedDate;
}
