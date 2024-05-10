package com.t2m.g2nee.shop.couponset.coupon.controller;

import com.t2m.g2nee.shop.couponset.coupon.dto.request.CouponDownloadDto;
import com.t2m.g2nee.shop.couponset.coupon.dto.request.CouponIssueDto;
import com.t2m.g2nee.shop.couponset.coupon.dto.response.CouponInfoDto;
import com.t2m.g2nee.shop.couponset.coupon.service.CouponService;
import com.t2m.g2nee.shop.pageUtils.PageResponse;
import javax.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/shop/coupon")
public class CouponController {

    private final CouponService couponService;

    public CouponController(CouponService couponService) {
        this.couponService = couponService;
    }

    @PostMapping
    public ResponseEntity<CouponInfoDto> issueCoupon(@RequestBody @Valid CouponIssueDto request) {

        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON)
                .body(couponService.issueCoupon(request));
    }

    @PostMapping("/download")
    public ResponseEntity<CouponInfoDto> downloadCoupon(@RequestBody @Valid CouponDownloadDto request) {
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON)
                .body(couponService.downloadCoupon(request));
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<PageResponse<CouponInfoDto>> getMyCoupons(@PathVariable("customerId") Long customerId,
                                                                    @RequestParam("page") int page) {
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON)
                .body(couponService.getMyCoupons(customerId, page));
    }

    @GetMapping("/{customerId}/book")
    public ResponseEntity<PageResponse<CouponInfoDto>> getBookCoupons(@PathVariable("customerId") Long customerId,
                                                                      @RequestParam("bookId") Long bookId,
                                                                      @RequestParam("page") int page) {
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON)
                .body(couponService.getBookCoupons(customerId, bookId, page));
    }

    @GetMapping("/{customerId}/all")
    public ResponseEntity<PageResponse<CouponInfoDto>> getTotalCoupons(@PathVariable("customerId") Long customerId,
                                                                       @RequestParam("page") int page) {
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON)
                .body(couponService.getTotalCoupons(customerId, page));
    }

}
