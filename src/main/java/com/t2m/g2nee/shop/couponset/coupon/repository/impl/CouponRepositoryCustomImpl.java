package com.t2m.g2nee.shop.couponset.coupon.repository.impl;

import com.t2m.g2nee.shop.bookset.bookcategory.domain.QBookCategory;
import com.t2m.g2nee.shop.couponset.bookcoupon.domain.QBookCoupon;
import com.t2m.g2nee.shop.couponset.categorycoupon.domain.QCategoryCoupon;
import com.t2m.g2nee.shop.couponset.coupon.domain.Coupon;
import com.t2m.g2nee.shop.couponset.coupon.domain.QCoupon;
import com.t2m.g2nee.shop.couponset.coupon.repository.CouponRepositoryCustom;
import com.t2m.g2nee.shop.couponset.coupontype.domain.QCouponType;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

public class CouponRepositoryCustomImpl extends QuerydslRepositorySupport implements CouponRepositoryCustom {
    public CouponRepositoryCustomImpl() {
        super(Coupon.class);
    }

    @Override
    public Page<Coupon> getBookCoupons(Long customerId, Long bookId, Pageable pageable) {
        QCoupon coupon = QCoupon.coupon;
        QCouponType couponType = QCouponType.couponType;
        QBookCoupon bookCoupon = QBookCoupon.bookCoupon;
        QCategoryCoupon categoryCoupon = QCategoryCoupon.categoryCoupon;
        QBookCategory bookCategory = QBookCategory.bookCategory;

        /**
         * select distinct *
         * from t2m_dev.Coupons c
         * inner join t2m_dev.CouponTypes ct on c.couponTypeId = ct.couponTypeId
         * left join t2m_dev.BookCoupon bc on ct.couponTypeId = bc.couponTypeId
         * left join t2m_dev.CategoryCoupon cc on ct.couponTypeId = cc.couponTypeId
         * left join t2m_dev.BookCategory bcr on bcr.categoryId = cc.categoryId
         * where c.customerId = ? and (bc.bookId = ? or bcr.bookId = ?) and c.status="NOTUSED";
         */

        List<Coupon> couponList = from(coupon)
                .innerJoin(couponType).on(couponType.couponTypeId.eq(coupon.couponType.couponTypeId))
                .leftJoin(bookCoupon).on(bookCoupon.couponTypeId.eq(couponType.couponTypeId))
                .leftJoin(categoryCoupon).on(categoryCoupon.couponTypeId.eq(couponType.couponTypeId))
                .leftJoin(bookCategory).on(bookCategory.category.categoryId.eq(categoryCoupon.category.categoryId))
                .where(coupon.member.customerId.eq(customerId)
                        .and(bookCoupon.book.bookId.eq(bookId).or(bookCategory.book.bookId.eq(bookId)))
                        .and(coupon.status.eq(Coupon.CouponStatus.NOTUSED)))
                .select(coupon)
                .orderBy(coupon.expirationDate.asc())
                .fetch();

        Long count = from(coupon)
                .innerJoin(couponType).on(couponType.couponTypeId.eq(coupon.couponType.couponTypeId))
                .leftJoin(bookCoupon).on(bookCoupon.couponTypeId.eq(couponType.couponTypeId))
                .leftJoin(categoryCoupon).on(categoryCoupon.couponTypeId.eq(couponType.couponTypeId))
                .leftJoin(bookCategory).on(bookCategory.category.categoryId.eq(categoryCoupon.category.categoryId))
                .where(coupon.member.customerId.eq(customerId)
                        .and(bookCoupon.book.bookId.eq(bookId).or(bookCategory.book.bookId.eq(bookId)))
                        .and(coupon.status.eq(Coupon.CouponStatus.NOTUSED)))
                .select(coupon.count())
                .fetchOne();


        return new PageImpl<>(couponList, pageable, count);

    }

    @Override
    public Page<Coupon> getTotalCoupons(Long customerId, Pageable pageable) {
        QCoupon coupon = QCoupon.coupon;
        QCouponType couponType = QCouponType.couponType;
        QBookCoupon bookCoupon = QBookCoupon.bookCoupon;
        QCategoryCoupon categoryCoupon = QCategoryCoupon.categoryCoupon;
        QBookCategory bookCategory = QBookCategory.bookCategory;

        /**
         * select distinct *
         * from t2m_dev.Coupons c
         * inner join t2m_dev.CouponTypes ct on c.couponTypeId = ct.couponTypeId
         * left join t2m_dev.BookCoupon bc on ct.couponTypeId = bc.couponTypeId
         * left join t2m_dev.CategoryCoupon cc on ct.couponTypeId = cc.couponTypeId
         * left join t2m_dev.BookCategory bcr on bcr.categoryId = cc.categoryId
         * where c.customerId = 8 and c.status="NOTUSED" and cc.categoryId is null and bc.bookId is null;
         */

        List<Coupon> couponList = from(coupon)
                .innerJoin(couponType).on(couponType.couponTypeId.eq(coupon.couponType.couponTypeId))
                .leftJoin(bookCoupon).on(bookCoupon.couponTypeId.eq(couponType.couponTypeId))
                .leftJoin(categoryCoupon).on(categoryCoupon.couponTypeId.eq(couponType.couponTypeId))
                .leftJoin(bookCategory).on(bookCategory.category.categoryId.eq(categoryCoupon.category.categoryId))
                .where(coupon.member.customerId.eq(customerId)
                        .and(categoryCoupon.category.categoryId.isNull())
                        .and(bookCoupon.book.bookId.isNull())
                        .and(coupon.status.eq(Coupon.CouponStatus.NOTUSED)))
                .select(coupon)
                .orderBy(coupon.expirationDate.asc())
                .fetch();

        Long count = from(coupon)
                .innerJoin(couponType).on(couponType.couponTypeId.eq(coupon.couponType.couponTypeId))
                .leftJoin(bookCoupon).on(bookCoupon.couponTypeId.eq(couponType.couponTypeId))
                .leftJoin(categoryCoupon).on(categoryCoupon.couponTypeId.eq(couponType.couponTypeId))
                .leftJoin(bookCategory).on(bookCategory.category.categoryId.eq(categoryCoupon.category.categoryId))
                .where(coupon.member.customerId.eq(customerId)
                        .and(categoryCoupon.category.categoryId.isNull())
                        .and(bookCoupon.book.bookId.isNull())
                        .and(coupon.status.eq(Coupon.CouponStatus.NOTUSED)))
                .select(coupon.count())
                .fetchOne();

        return new PageImpl<>(couponList, pageable, count);
    }
}
