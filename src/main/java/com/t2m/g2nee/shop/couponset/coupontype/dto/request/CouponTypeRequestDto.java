package com.t2m.g2nee.shop.couponset.coupontype.dto.request;

import com.t2m.g2nee.shop.couponset.coupontype.domain.CouponType;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CouponTypeRequestDto {

    private Long couponTypeId;
    private String name;
    private Integer period;
    private CouponType.Type type;
    private BigDecimal discount;
    private BigDecimal minimumOrderAmount;
    private BigDecimal maximumDiscount;
    private CouponType.CouponTypeStatus status;
    private Long bookId;
    private Long categoryId;


}
