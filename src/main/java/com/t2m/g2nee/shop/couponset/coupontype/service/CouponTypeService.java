package com.t2m.g2nee.shop.couponset.coupontype.service;

import com.t2m.g2nee.shop.couponset.coupontype.dto.request.CouponTypeRequestDto;
import com.t2m.g2nee.shop.couponset.coupontype.dto.response.CouponTypeCreatedDto;
import com.t2m.g2nee.shop.couponset.coupontype.dto.response.CouponTypeInfoDto;
import com.t2m.g2nee.shop.pageUtils.PageResponse;
import org.springframework.stereotype.Service;

/**
 * 쿠폰목록 서비스를 위한 인터페이스입니다.
 *
 * @author : 김수현
 * @since : 1.0
 */

@Service
public interface CouponTypeService {

    /**
     * customerId로 각 고객의 쿠폰을 조회할 수 있는 Service
     * @param customerId
     * @return
     */
    List<CouponTypeInfoDto> findAllByCustomer_CustomerId(Long customerId);

    /**
     * 관리자가 모든 쿠폰을 조회할 수 있는 Service
     * @param page
     * @return
     */
    PageResponse<CouponTypeInfoDto> getAllCouponTypes(int page);

    CouponTypeCreatedDto createCouponType(CouponTypeRequestDto couponTypeRequestDto);

    CouponTypeCreatedDto createBookCoupon(CouponTypeRequestDto couponTypeRequestDto);

    CouponTypeCreatedDto createCategoryCoupon(CouponTypeRequestDto couponTypeRequestDto);

    /**
     * 하나의 쿠폰을 얻습니다.
     *
     * @param couponTypeId 쿠폰 종류 id
     * @return CouponType객체
     * @author : 김수빈
     */
    CouponType getCoupon(Long couponTypeId);



}
