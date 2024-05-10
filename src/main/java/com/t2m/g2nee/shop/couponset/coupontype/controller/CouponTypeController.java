package com.t2m.g2nee.shop.couponset.coupontype.controller;


import com.t2m.g2nee.shop.couponset.coupontype.service.CouponTypeService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1/shop/couponType")
public class CouponTypeController {

    private final CouponTypeService couponTypeService;

    public CouponTypeController(CouponTypeService couponTypeService) {
        this.couponTypeService = couponTypeService;
    }


}
