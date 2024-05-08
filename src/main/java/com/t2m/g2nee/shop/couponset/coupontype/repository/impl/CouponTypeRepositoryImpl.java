package com.t2m.g2nee.shop.couponset.coupontype.repository.impl;


import com.t2m.g2nee.shop.couponset.coupon.domain.Coupon;
import com.t2m.g2nee.shop.couponset.coupon.domain.QCoupon;
import com.t2m.g2nee.shop.couponset.coupontype.dto.CouponTypeInfoDto;
import com.t2m.g2nee.shop.couponset.coupontype.repository.CustomCouponTypeRepository;
import com.t2m.g2nee.shop.memberset.customer.domain.QCustomer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

public class CouponTypeRepositoryImpl extends QuerydslRepositorySupport implements CustomCouponTypeRepository {



    public CouponTypeRepositoryImpl() {
        super(Coupon.class);
    }

    @Override
    public Page<CouponTypeInfoDto> getAllCoupons(Long customerId, Pageable pageable) {

        QCoupon coupon =QCoupon.coupon;
        QCustomer customer = QCustomer.customer;


        List<CouponTypeInfoDto> couponList = from();

        int count=0;


        return new PageImpl<>(couponList,pageable,count);
    }
}