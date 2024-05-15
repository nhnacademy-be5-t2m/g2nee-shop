package com.t2m.g2nee.shop.couponset.coupontype.service;

import com.t2m.g2nee.shop.couponset.coupontype.dto.request.CouponTypeRequestDto;
import com.t2m.g2nee.shop.couponset.coupontype.dto.response.CouponTypeCreatedDto;
import com.t2m.g2nee.shop.couponset.coupontype.dto.response.CouponTypeInfoDto;
import com.t2m.g2nee.shop.pageUtils.PageResponse;
import org.springframework.stereotype.Service;

/**
 * 마이페이지 쿠폰확인 서비스를 위한 인터페이스입니다.
 * @author : 김수현
 * @since : 1.0
 */

public interface CouponTypeService {


    /**
     * 관리자가 모든 쿠폰을 조회할 수 있는 Service
     * @param page
     * @return
     */
    PageResponse<CouponTypeInfoDto> getAllCouponTypes(int page);

    CouponTypeCreatedDto createCouponType(CouponTypeRequestDto couponTypeRequestDto);

    CouponTypeCreatedDto createBookCoupon(CouponTypeRequestDto couponTypeRequestDto);

    CouponTypeCreatedDto createCategoryCoupon(CouponTypeRequestDto couponTypeRequestDto);





}
