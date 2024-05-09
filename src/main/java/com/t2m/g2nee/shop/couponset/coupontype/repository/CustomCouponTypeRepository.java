package com.t2m.g2nee.shop.couponset.coupontype.repository;

import com.t2m.g2nee.shop.couponset.coupon.domain.Coupon;
import com.t2m.g2nee.shop.couponset.coupontype.dto.CouponTypeInfoDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * 쿠폰조회 관련 Repository
 * @author : 김수현
 * @since : 1.0
 */
public interface CustomCouponTypeRepository {

    /**
     * admin에서 쿠폰 조회하는 controller
     * @param pageable
     * @return
     */
   Page<CouponTypeInfoDto> getAllCoupons(Pageable pageable);



}
