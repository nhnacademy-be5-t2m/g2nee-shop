package com.t2m.g2nee.shop.couponset.bookcoupon.service.impl;

import com.t2m.g2nee.shop.couponset.bookcoupon.domain.BookCoupon;
import com.t2m.g2nee.shop.couponset.bookcoupon.dto.request.BookCouponRequestDto;
import com.t2m.g2nee.shop.couponset.bookcoupon.dto.response.BookCouponCreatedDto;
import com.t2m.g2nee.shop.couponset.bookcoupon.repository.BookCouponRepository;
import com.t2m.g2nee.shop.couponset.bookcoupon.service.BookCouponService;
import org.springframework.stereotype.Service;

@Service
public class BookCouponServiceImpl implements BookCouponService {
    private final BookCouponRepository bookCouponRepository;

    public BookCouponServiceImpl(BookCouponRepository bookCouponRepository){
        this.bookCouponRepository =bookCouponRepository;
    }
    @Override
    public BookCouponCreatedDto createBookCoupon(BookCouponRequestDto bookCouponRequestDto) {
        BookCoupon bookCoupon = BookCoupon.builder()
                .couponTypeId(bookCouponRequestDto.getCouponTypeId())
                .name(bookCouponRequestDto.getName())
                .period(bookCouponRequestDto.getPeriod())
                .type(bookCouponRequestDto.getType())
                .discount(bookCouponRequestDto.getDiscount())
                .minimumOrderAmount(bookCouponRequestDto.getMinimumOrderAmount())
                .maximumDiscount(bookCouponRequestDto.getMaximumDiscount())
                .status(bookCouponRequestDto.getStatus())
                .build();

        BookCoupon savedBookCoupon=(BookCoupon) bookCouponRepository.save(bookCoupon);

        return null;
    }
}
