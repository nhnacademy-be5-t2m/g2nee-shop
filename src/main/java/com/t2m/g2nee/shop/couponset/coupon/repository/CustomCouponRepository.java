package com.t2m.g2nee.shop.couponset.coupon.repository;

import com.t2m.g2nee.shop.couponset.coupon.dto.CouponInfoDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * 쿠폰 조회 controller
 * @author : 김수현
 * @since : 1.0
 */

public interface CustomCouponRepository {

    /**
     * admin에서 쿠폰 조회하는 controller
     * @param customerId
     * @param pageable
     * @return
     */
    Page<CouponInfoDto> getAllCoupons(Long customerId, Pageable pageable);

}
