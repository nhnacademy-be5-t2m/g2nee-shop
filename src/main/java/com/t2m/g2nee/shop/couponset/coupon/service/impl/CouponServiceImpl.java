package com.t2m.g2nee.shop.couponset.coupon.service.impl;

import com.t2m.g2nee.shop.couponset.coupon.domain.Coupon;
import com.t2m.g2nee.shop.couponset.coupon.repository.CouponRepository;
import com.t2m.g2nee.shop.couponset.coupon.service.CouponService;
import com.t2m.g2nee.shop.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CouponServiceImpl implements CouponService {

    private final CouponRepository couponRepository;

    @Override
    public Coupon getCoupon(Long couponId) {
        return couponRepository.findById(couponId).orElseThrow(() -> new NotFoundException("쿠폰이 존재하지 않습니다."));
    }
}
