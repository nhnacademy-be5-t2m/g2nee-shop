package com.t2m.g2nee.shop.couponset.coupon.repository;

import com.t2m.g2nee.shop.couponset.coupon.domain.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface CouponRepository extends JpaRepository<Coupon,Long> {

//querydsl

    List<Coupon> findAllByCustomer_CustomerId(Long customerId);


}
