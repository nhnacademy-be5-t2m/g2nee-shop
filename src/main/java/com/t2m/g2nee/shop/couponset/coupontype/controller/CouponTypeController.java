package com.t2m.g2nee.shop.couponset.coupontype.controller;


import com.t2m.g2nee.shop.couponset.coupontype.dto.CouponTypeInfoDto;
import com.t2m.g2nee.shop.couponset.coupontype.dto.response.CouponTypeCreatedDto;
import com.t2m.g2nee.shop.couponset.coupontype.dto.request.CouponTypeRequestDto;
import com.t2m.g2nee.shop.couponset.coupontype.service.CouponTypeService;
import com.t2m.g2nee.shop.pageUtils.PageResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 쿠폰조회와 쿠폰 생성 Controller
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


    /**
     * 관리자가  모든 쿠폰을 조회할 수 있는 메소드
     * @param page
     * @return
     */
    @GetMapping
    public ResponseEntity<PageResponse<CouponTypeInfoDto>> getAllCouponTypes(@RequestParam(defaultValue = "1") int page ) {


        PageResponse<CouponTypeInfoDto> responses =  couponTypeService.getAllCouponTypes(page);

        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(responses);

    }

    /**
     * 관리자가 일반 쿠폰을 생성할 수 있는 메소드
     * @param couponTypeRequestDto
     * @return
     */

    @PostMapping("createCouponType")
    public ResponseEntity<CouponTypeCreatedDto> createCouponType(@RequestBody CouponTypeRequestDto couponTypeRequestDto){
        CouponTypeCreatedDto couponTypeCreatedDto  = couponTypeService.createCouponType(couponTypeRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(couponTypeCreatedDto);

    }
    /**
     * 관리자가 도서 쿠폰을 생성할 수 있는 메소드
     * @param couponTypeRequestDto
     * @return
     */
    @PostMapping("createBookCoupon")
    public ResponseEntity<CouponTypeCreatedDto> createBookCoupon(@RequestBody CouponTypeRequestDto couponTypeRequestDto){
        CouponTypeCreatedDto couponTypeCreatedDto  = couponTypeService.createBookCoupon(couponTypeRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(couponTypeCreatedDto);

    }

    /**
     * 관리자가 카테고리 쿠폰을 생성할 수 있는 메소드
     * @param couponTypeRequestDto
     * @return
     */
    @PostMapping("createCategoryCoupon")
    public ResponseEntity<CouponTypeCreatedDto> createCategoryCoupon(@RequestBody CouponTypeRequestDto couponTypeRequestDto){
        CouponTypeCreatedDto couponTypeCreatedDto  = couponTypeService.createCategoryCoupon(couponTypeRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(couponTypeCreatedDto);

    }



}
