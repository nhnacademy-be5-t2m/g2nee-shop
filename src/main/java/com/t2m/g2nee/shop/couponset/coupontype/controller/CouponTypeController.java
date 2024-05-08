package com.t2m.g2nee.shop.couponset.coupontype.controller;


import com.t2m.g2nee.shop.couponset.coupontype.dto.CouponTypeInfoDto;
import com.t2m.g2nee.shop.couponset.coupontype.service.CouponTypeService;
import com.t2m.g2nee.shop.pageUtils.PageResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/shop/couponType")
public class CouponTypeController {

    private final CouponTypeService couponTypeService;

    public CouponTypeController(CouponTypeService couponTypeService) {
        this.couponTypeService = couponTypeService;
    }

    @GetMapping("/coupons/{customerId}")
    public List<CouponTypeInfoDto> getAllCouponsByCustomerId(@PathVariable("customerId") Long customerId) {
        return couponTypeService.findAllByCustomer_CustomerId(customerId);
    }


    @GetMapping("/coupons/")
    public ResponseEntity<PageResponse<CouponTypeInfoDto>> getAllCoupons(@PathVariable("customerId") Long customerId, @RequestParam(defaultValue = "1") int page) {


        PageResponse<CouponTypeInfoDto> responses = (PageResponse<CouponTypeInfoDto>) couponTypeService.getAllCoupons(customerId,page);

        return ResponseEntity.status(HttpStatus.OK).body(responses);

    }



}
