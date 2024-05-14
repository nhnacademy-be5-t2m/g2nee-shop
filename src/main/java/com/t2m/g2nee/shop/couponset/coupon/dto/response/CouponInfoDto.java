package com.t2m.g2nee.shop.couponset.coupon.dto.response;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class CouponInfoDto {
    private Long couponId;
    private String expirationDate;
    private String issuedDate;
    private String status;
    private String discount;
    private BigDecimal maximumDiscount;
    private BigDecimal minimumOrderAmount;
    private String name;
    private int period;
    private String type;
    private String target;
}