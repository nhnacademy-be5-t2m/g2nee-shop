package com.t2m.g2nee.shop.couponset.coupontype.repository.impl;

import com.querydsl.core.types.Projections;
import com.t2m.g2nee.shop.couponset.bookcoupon.domain.QBookCoupon;
import com.t2m.g2nee.shop.couponset.categorycoupon.domain.QCategoryCoupon;
import com.t2m.g2nee.shop.couponset.coupon.domain.Coupon;
import com.t2m.g2nee.shop.couponset.coupontype.domain.QCouponType;
import com.t2m.g2nee.shop.couponset.coupontype.dto.CouponTypeInfoDto;
import com.t2m.g2nee.shop.couponset.coupontype.repository.CustomCouponTypeRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public class CouponTypeRepositoryImpl extends QuerydslRepositorySupport implements CustomCouponTypeRepository {
    //querydsl


    public CouponTypeRepositoryImpl() {
        super(Coupon.class);
    }

    @Override
    public Page<CouponTypeInfoDto> getAllCoupons(Pageable pageable) {


        QCouponType couponType = QCouponType.couponType;
        QCategoryCoupon categoryCoupon = QCategoryCoupon.categoryCoupon;
        QBookCoupon bookCoupon = QBookCoupon.bookCoupon;

        List<CouponTypeInfoDto> couponList =
                from(couponType)
                        .leftJoin(categoryCoupon).on(couponType.couponTypeId.eq(categoryCoupon.couponTypeId))
                        .leftJoin(bookCoupon).on(couponType.couponTypeId.eq(bookCoupon.couponTypeId))
                        .select(
                                Projections.fields(
                                        CouponTypeInfoDto.class,
                                        couponType.couponTypeId,
                                        couponType.name.as("couponTypeName"),
                                        couponType.period,
                                        couponType.type.stringValue().as("type"),
                                        couponType.discount,
                                        couponType.minimumOrderAmount,
                                        couponType.maximumDiscount,
                                        categoryCoupon.category.categoryId.as("categoryId"),
                                        bookCoupon.book.bookId.as("bookId"),
                                        couponType.status.stringValue().as("status")

                                )
                        )
                        .offset(pageable.getOffset())
                        .limit(pageable.getPageSize())
                        .fetch();

        // 전체 레코드 수 조회
        long totalCount = from(couponType).select(couponType.count()).fetchOne();

        return new PageImpl<>(couponList, pageable, totalCount);
    }


    }










