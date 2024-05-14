package com.t2m.g2nee.shop.couponset.bookcoupon.dto.request;

import com.t2m.g2nee.shop.couponset.bookcoupon.domain.BookCoupon;
import com.t2m.g2nee.shop.couponset.coupontype.domain.CouponType;
import com.t2m.g2nee.shop.couponset.coupontype.dto.request.CouponTypeRequestDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class BookCouponRequestDto extends CouponTypeRequestDto {

    private Long bookId;
}
