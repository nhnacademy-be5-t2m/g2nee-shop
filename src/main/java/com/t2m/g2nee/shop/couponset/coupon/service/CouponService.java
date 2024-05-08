package com.t2m.g2nee.shop.couponset.coupon.service;

import com.t2m.g2nee.shop.couponset.coupon.domain.Coupon;

public interface CouponService {

    /**
     * 쿠폰 id로 쿠폰을 얻는 메소드 입니다.
     *
     * @param couponId 쿠폰 id
     * @return 쿠폰 객체
     */
    Coupon getCoupon(Long couponId);
}
