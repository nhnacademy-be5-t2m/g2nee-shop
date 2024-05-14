package com.t2m.g2nee.shop.couponset.categorycoupon.service.impl;

import com.t2m.g2nee.shop.bookset.category.domain.Category;
import com.t2m.g2nee.shop.bookset.category.repository.CategoryRepository;
import com.t2m.g2nee.shop.couponset.categorycoupon.domain.CategoryCoupon;
import com.t2m.g2nee.shop.couponset.categorycoupon.dto.request.CategoryCouponRequestDto;
import com.t2m.g2nee.shop.couponset.categorycoupon.dto.response.CategoryCouponCreatedDto;
import com.t2m.g2nee.shop.couponset.categorycoupon.service.CategoryCouponService;
import com.t2m.g2nee.shop.couponset.coupontype.domain.CouponType;
import com.t2m.g2nee.shop.couponset.coupontype.repository.CouponTypeRepository;
import com.t2m.g2nee.shop.exception.NotFoundException;
import org.springframework.stereotype.Service;

@Service

    public class CategoryCouponServiceImpl implements CategoryCouponService {

        private final CategoryRepository categoryRepository;
        private final CouponTypeRepository couponTypeRepository;

        public CategoryCouponServiceImpl(CategoryRepository categoryRepository, CouponTypeRepository couponTypeRepository) {
            this.categoryRepository = categoryRepository;
            this.couponTypeRepository = couponTypeRepository;
        }

        @Override
        public CategoryCouponCreatedDto createCategoryCoupon(CategoryCouponRequestDto categoryCouponRequestDto) {


            Category category = categoryRepository.findById(categoryCouponRequestDto.getCategoryId())
                    .orElseThrow(()-> new NotFoundException("카테고리를 찾을 수 없습니다."+ categoryCouponRequestDto.getCategoryId()));

            CategoryCoupon categoryCouponInfo = CategoryCoupon.builder()
                    .couponTypeId(categoryCouponRequestDto.getCouponTypeId())
                    .name(categoryCouponRequestDto.getName())
                    .period(categoryCouponRequestDto.getPeriod())
                    .type(categoryCouponRequestDto.getType())
                    .discount(categoryCouponRequestDto.getDiscount())
                    .minimumOrderAmount(categoryCouponRequestDto.getMinimumOrderAmount())
                    .maximumDiscount(categoryCouponRequestDto.getMaximumDiscount())
                    .status(categoryCouponRequestDto.getStatus())
                    .category(category)
                    .build();

            CouponType couponType = (CouponType) couponTypeRepository.save(categoryCouponInfo);

            CategoryCoupon categoryCoupon = (CategoryCoupon) couponTypeRepository.save(categoryCouponInfo);


            return new CategoryCouponCreatedDto(categoryCouponRequestDto.getName(), categoryCouponRequestDto.getPeriod(), categoryCouponRequestDto.getType(),categoryCouponRequestDto.getDiscount(),categoryCouponRequestDto.getMinimumOrderAmount(),categoryCouponRequestDto.getMaximumDiscount(),categoryCouponRequestDto.getStatus());
        }
    }



