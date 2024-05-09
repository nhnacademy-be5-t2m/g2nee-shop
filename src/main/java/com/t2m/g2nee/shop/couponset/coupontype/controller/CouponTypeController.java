package com.t2m.g2nee.shop.couponset.coupontype.controller;


import com.t2m.g2nee.shop.couponset.coupontype.dto.CouponTypeInfoDto;
import com.t2m.g2nee.shop.couponset.coupontype.service.CouponTypeService;
import com.t2m.g2nee.shop.pageUtils.PageResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 쿠폰조회 Controller
 * @author : 김수현
 * @since : 1.0
 */
@RestController
@RequestMapping("/api/v1/shop/couponType")
public class CouponTypeController {

    private final CouponTypeService couponTypeService;

    public CouponTypeController(CouponTypeService couponTypeService) {
        this.couponTypeService = couponTypeService;
    }




    @GetMapping
    public ResponseEntity<PageResponse<CouponTypeInfoDto>> getAllCoupons(@RequestParam(defaultValue = "1") int page ) {


        PageResponse<CouponTypeInfoDto> responses =  couponTypeService.getAllCoupons(page);

        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(responses);

    }



}
