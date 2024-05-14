package com.t2m.g2nee.shop.couponset.categorycoupon.dto.request;

import com.t2m.g2nee.shop.couponset.coupontype.domain.CouponType;
import com.t2m.g2nee.shop.couponset.coupontype.dto.request.CouponTypeRequestDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CategoryCouponRequestDto extends CouponTypeRequestDto {

    private Long categoryId;
}
