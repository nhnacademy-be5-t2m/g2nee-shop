package com.t2m.g2nee.shop.couponset.bookcoupon.dto.response;

import com.t2m.g2nee.shop.couponset.coupontype.domain.CouponType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class BookCouponCreatedDto {
    private String name;
    private Integer period;
    private CouponType.Type type;
    private BigDecimal discount;
    private BigDecimal minimumOrderAmount;
    private BigDecimal maximumDiscount;
    private CouponType.CouponTypeStatus status;
}
