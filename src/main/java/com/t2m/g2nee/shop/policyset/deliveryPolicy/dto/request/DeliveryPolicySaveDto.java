package com.t2m.g2nee.shop.policyset.deliveryPolicy.dto.request;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 배송비 정책 저장을 위한 객체입니다.
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class DeliveryPolicySaveDto {

    /**
     * 배송비
     */
    @NotNull(message = "가격을 입력해주세요")
    @Min(value = 0, message = "가격은 음수가 될 수 없습니다.")
    private int deliveryFee;

    /**
     * 무료 배송 기준
     */
    @NotNull(message = "가격을 입력해주세요")
    @Min(value = 0, message = "가격은 음수가 될 수 없습니다.")
    private int freeDeliveryStandard;
}
