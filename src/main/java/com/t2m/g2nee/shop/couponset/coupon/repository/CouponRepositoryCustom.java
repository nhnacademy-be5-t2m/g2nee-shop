package com.t2m.g2nee.shop.couponset.coupon.repository;

import com.t2m.g2nee.shop.couponset.coupon.domain.Coupon;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CouponRepositoryCustom {

    Page<Coupon> getBookCoupons(Long customerId, Long bookId, Pageable pageable);

    Page<Coupon> getTotalCoupons(Long customerId, Pageable pageable);
}
