package com.t2m.g2nee.shop.couponset.categorycoupon.controller;

import com.t2m.g2nee.shop.couponset.categorycoupon.domain.CategoryCoupon;
import com.t2m.g2nee.shop.couponset.categorycoupon.dto.request.CategoryCouponRequestDto;
import com.t2m.g2nee.shop.couponset.categorycoupon.dto.response.CategoryCouponCreatedDto;
import com.t2m.g2nee.shop.couponset.categorycoupon.service.CategoryCouponService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/shop/couponType")
public class CategoryCouponController {
    private final CategoryCouponService categoryCouponService;

    public CategoryCouponController(CategoryCouponService categoryCouponService){
        this.categoryCouponService = categoryCouponService;
    }

    @PostMapping("createdCategoryCouponInfo")
    public ResponseEntity<CategoryCouponCreatedDto> createCategoryCoupon(@RequestBody CategoryCouponRequestDto categoryCouponRequestDto){
        CategoryCouponCreatedDto categoryCouponCreatedDto = categoryCouponService.createCategoryCoupon(categoryCouponRequestDto);

        return ResponseEntity.status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(categoryCouponCreatedDto);
    }
}
