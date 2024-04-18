package com.t2m.g2nee.shop.policyset.deliveryPolicy.dto.request;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class DeliveryPolicySaveDto {

    @NotNull(message = "가격을 입력해주세요")
    @Min(value = 0, message = "가격은 음수가 될 수 없습니다.")
    private int deliveryFee;

    @NotNull(message = "가격을 입력해주세요")
    @Min(value = 0, message = "가격은 음수가 될 수 없습니다.")
    private int freeDeliveryStandard;
}
