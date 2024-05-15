package com.t2m.g2nee.shop.couponset.coupontype.repository;

import com.t2m.g2nee.shop.couponset.coupontype.domain.CouponType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface CouponTypeRepository extends JpaRepository<CouponType,Long>, CustomCouponTypeRepository {

}