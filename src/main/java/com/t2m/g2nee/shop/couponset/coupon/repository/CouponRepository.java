package com.t2m.g2nee.shop.couponset.coupon.repository;

import com.t2m.g2nee.shop.couponset.coupon.domain.Coupon;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CouponRepository extends JpaRepository<Coupon, Long>, CouponRepositoryCustom {

    boolean existsByCouponType_CouponTypeIdAndMember_CustomerId(Long couponTypeId, Long customerId);

    boolean existsByCouponType_CouponTypeId(Long couponTypeId);


    @Query(value = "SELECT * FROM t2m_dev.Coupons " +
            "WHERE customerId = :customerId " +
            "ORDER BY (CASE WHEN status = 'NOTUSED' THEN 1 ELSE 2 END), expirationDate ASC",
            nativeQuery = true)
    Page<Coupon> getCouponsByCustomerId(@Param("customerId") Long customerId,
                                        Pageable pageable);

}
