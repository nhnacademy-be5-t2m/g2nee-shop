package com.t2m.g2nee.shop.policyset.deliveryPolicy.dto.request;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class DeliveryPolicySaveDto {

    @Pattern(regexp = "\\d+", message = "가격은 숫자로 입력해주세요")
    private int deliveryFee;

    @Pattern(regexp = "\\d+", message = "가격은 숫자로 입력해주세요")
    private int freeDeliveryStandard;

    @NotNull(message = "배송비 활성 정보가 없음!")
    private Boolean isActivated;
}
