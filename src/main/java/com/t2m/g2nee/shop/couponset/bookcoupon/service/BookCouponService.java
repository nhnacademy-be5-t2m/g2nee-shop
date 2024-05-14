package com.t2m.g2nee.shop.couponset.bookcoupon.service;

import com.t2m.g2nee.shop.couponset.bookcoupon.dto.request.BookCouponRequestDto;
import com.t2m.g2nee.shop.couponset.bookcoupon.dto.response.BookCouponCreatedDto;
import org.springframework.stereotype.Service;

public interface BookCouponService {

    BookCouponCreatedDto createBookCoupon(BookCouponRequestDto bookCouponRequestDto);

}
