package com.t2m.g2nee.shop.couponset.categorycoupon.service.impl;

import com.t2m.g2nee.shop.couponset.categorycoupon.domain.CategoryCoupon;
import com.t2m.g2nee.shop.couponset.categorycoupon.repository.CategoryCouponRepository;
import com.t2m.g2nee.shop.couponset.categorycoupon.service.CategoryCouponService;
import org.springframework.stereotype.Service;

@Service
public class CategoryCouponServiceImpl implements CategoryCouponService {
    private final CategoryCouponRepository categoryCouponRepository;

    public CategoryCouponServiceImpl(CategoryCouponRepository categoryCouponRepository) {
        this.categoryCouponRepository = categoryCouponRepository;
    }


    @Override
    public CategoryCoupon getCategoryCoupon(Long couponTypeId) {
        return categoryCouponRepository.findById(couponTypeId).orElse(null);
    }
}
