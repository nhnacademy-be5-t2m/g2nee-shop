package com.t2m.g2nee.shop.couponset.categorycoupon.service;

import com.t2m.g2nee.shop.couponset.categorycoupon.dto.request.CategoryCouponRequestDto;
import com.t2m.g2nee.shop.couponset.categorycoupon.dto.response.CategoryCouponCreatedDto;
import com.t2m.g2nee.shop.couponset.coupontype.dto.request.CouponTypeRequestDto;
import org.springframework.stereotype.Service;

/**
 * 카테고리 쿠폰 생성을 위한 Service
 * @author : 김수현
 * @since : 1.0
 */
public interface CategoryCouponService  {
    CategoryCouponCreatedDto createCategoryCoupon(CategoryCouponRequestDto categoryCouponRequestDto);

}
