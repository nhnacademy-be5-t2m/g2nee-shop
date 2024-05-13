package com.t2m.g2nee.shop.couponset.coupontype.service.impl;

import com.t2m.g2nee.shop.couponset.coupontype.domain.CouponType;
import com.t2m.g2nee.shop.couponset.coupontype.repository.CouponTypeRepository;
import com.t2m.g2nee.shop.couponset.coupontype.service.CouponTypeService;
import com.t2m.g2nee.shop.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


/**
 * 쿠폰 controller 클래스
 *
 * @author :김수현
 * @since :1.0
 */
@Service
@RequiredArgsConstructor
public class CouponTypeServiceImpl implements CouponTypeService {

    private final CouponTypeRepository couponTypeRepository;


    @Override
    public CouponType getCoupon(Long couponTypeId) {
        return couponTypeRepository.findById(couponTypeId).orElseThrow(
                () -> new NotFoundException("존재하지 않는 쿠폰입니다."));
    }
}
