package com.t2m.g2nee.shop.couponset.coupontype.service;

import com.t2m.g2nee.shop.couponset.coupontype.dto.CouponTypeInfoDto;
import com.t2m.g2nee.shop.pageUtils.PageResponse;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 마이페이지 쿠폰확인 서비스를 위한 인터페이스입니다.
 * @author : 김수현
 * @since : 1.0
 */

@Service
public interface CouponTypeService {


    /**
     * 관리자가 모든 쿠폰을 조회할 수 있는 Service
     * @param page
     * @return
     */
    PageResponse<CouponTypeInfoDto> getAllCoupons(int page);





}
