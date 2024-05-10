package com.t2m.g2nee.shop.couponset.coupontype.repository.impl;


import com.t2m.g2nee.shop.couponset.coupon.domain.Coupon;
import com.t2m.g2nee.shop.couponset.coupontype.dto.CouponTypeInfoDto;
import com.t2m.g2nee.shop.couponset.coupontype.repository.CustomCouponTypeRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

public class CouponTypeRepositoryImpl extends QuerydslRepositorySupport implements CustomCouponTypeRepository {



    public CouponTypeRepositoryImpl() {
        super(Coupon.class);
    }

    @Override
    public Page<CouponTypeInfoDto> getAllCoupons(Long customerId, Pageable pageable) {
        return null;
    }
}