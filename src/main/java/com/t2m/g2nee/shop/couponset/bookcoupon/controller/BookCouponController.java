package com.t2m.g2nee.shop.couponset.bookcoupon.controller;

import com.t2m.g2nee.shop.couponset.bookcoupon.dto.request.BookCouponRequestDto;
import com.t2m.g2nee.shop.couponset.bookcoupon.dto.response.BookCouponCreatedDto;
import com.t2m.g2nee.shop.couponset.bookcoupon.service.BookCouponService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * bookCoupon을 생성하는 Controller
 * @author : 김수현
 * @since : 1.0
 */
@RestController
@RequestMapping("/api/vi/shop/couponType")
public class BookCouponController {
    private final BookCouponService bookCouponService;

    public BookCouponController(BookCouponService bookCouponService) {
        this.bookCouponService = bookCouponService;
    }

    @PostMapping("createCategoryCoupon")
    public ResponseEntity<BookCouponCreatedDto>createCategoryCoupon(BookCouponRequestDto bookCouponRequestDto){
        BookCouponCreatedDto bookCouponCreatedDto = bookCouponService.createBookCoupon(bookCouponRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(bookCouponCreatedDto);
    }

}
