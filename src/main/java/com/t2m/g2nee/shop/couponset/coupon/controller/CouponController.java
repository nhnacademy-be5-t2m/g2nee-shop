package com.t2m.g2nee.shop.couponset.coupon.controller;

import com.t2m.g2nee.shop.couponset.coupon.dto.CouponInfoDto;
import com.t2m.g2nee.shop.pageUtils.PageResponse;
import com.t2m.g2nee.shop.review.dto.ReviewDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.t2m.g2nee.shop.couponset.coupon.service.CouponService;
import java.util.List;


@RestController
@RequestMapping("/api/v1/shop/coupon")
public class CouponController {

    private final CouponService couponService;

    public CouponController(CouponService couponService) {
        this.couponService = couponService;
    }

    @GetMapping("/coupons/{customerId}")
    public List<CouponInfoDto> getAllCouponsByCustomerId(@PathVariable("customerId") Long customerId) {
        return couponService.findAllByCustomer_CustomerId(customerId);
    }


    @GetMapping("/coupons/")
    public ResponseEntity<PageResponse<CouponInfoDto>> getAllCoupons(@PathVariable("customerId") Long customerId, @RequestParam(defaultValue = "1") int page) {


        PageResponse<CouponInfoDto> responses = (PageResponse<CouponInfoDto>) couponService.getAllCoupons(customerId,page);

        return ResponseEntity.status(HttpStatus.OK).body(responses);

    }



}

