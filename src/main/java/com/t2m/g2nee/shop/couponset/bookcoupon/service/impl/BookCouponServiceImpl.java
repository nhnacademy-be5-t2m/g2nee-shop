package com.t2m.g2nee.shop.couponset.bookcoupon.service.impl;

import com.t2m.g2nee.shop.couponset.bookcoupon.domain.BookCoupon;
import com.t2m.g2nee.shop.couponset.bookcoupon.repository.BookCouponRepository;
import com.t2m.g2nee.shop.couponset.bookcoupon.service.BookCouponService;
import org.springframework.stereotype.Service;

@Service
public class BookCouponServiceImpl implements BookCouponService {

    private final BookCouponRepository bookCouponRepository;

    public BookCouponServiceImpl(BookCouponRepository bookCouponRepository) {
        this.bookCouponRepository = bookCouponRepository;
    }

    @Override
    public BookCoupon getBookCoupon(Long couponTypeId) {
        return bookCouponRepository.findById(couponTypeId).orElse(null);
    }
}
