package com.t2m.g2nee.shop.couponset.coupon.dto.request;

import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class CouponIssueDto {
    @NotNull(message = "쿠폰 정보가 없습니다.")
    private Long couponTypeId;
}
