package com.t2m.g2nee.shop.couponset.coupontype.repository;

import com.t2m.g2nee.shop.couponset.coupon.domain.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface CouponTypeRepository extends JpaRepository<Coupon,Long> {

//querydsl

    List<Coupon> findAllByCustomer_CustomerId(Long customerId);


}