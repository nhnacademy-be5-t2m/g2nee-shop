package com.t2m.g2nee.shop.couponset.coupontype.service;

import com.t2m.g2nee.shop.couponset.coupontype.domain.CouponType;

/**
 * 쿠폰목록 서비스를 위한 인터페이스입니다.
 *
 * @author : 김수현
 * @since : 1.0
 */

public interface CouponTypeService {


    /**
     * 하나의 쿠폰을 얻습니다.
     *
     * @param couponTypeId 쿠폰 종류 id
     * @return CouponType객체
     * @author : 김수빈
     */
    CouponType getCoupon(Long couponTypeId);
}
