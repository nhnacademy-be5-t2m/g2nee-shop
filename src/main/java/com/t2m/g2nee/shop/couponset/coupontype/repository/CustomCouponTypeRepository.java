package com.t2m.g2nee.shop.couponset.coupontype.repository;

import com.t2m.g2nee.shop.couponset.coupontype.dto.CouponTypeInfoDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface CustomCouponTypeRepository {

    /**
     * admin에서 쿠폰 조회하는 controller
     * @param customerId
     * @param pageable
     * @return
     */
    Page<CouponTypeInfoDto> getAllCoupons(Long customerId, Pageable pageable);

}
