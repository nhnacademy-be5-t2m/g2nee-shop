package com.t2m.g2nee.shop.couponset.coupon.repository;

import static java.sql.Timestamp.valueOf;

import com.t2m.g2nee.shop.couponset.coupon.domain.Coupon;
import java.sql.PreparedStatement;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class CouponSaveRepository {

    private final JdbcTemplate jdbcTemplate;


    public CouponSaveRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Transactional
    public void saveAll(List<Coupon> coupons) {
        String sql = "INSERT INTO Coupons (issuedDate, expirationDate, status, couponTypeId, customerId) " +
                "VALUES (?, ?, ?, ?, ?)";

        jdbcTemplate.batchUpdate(sql,
                coupons,
                coupons.size(),
                (PreparedStatement ps, Coupon coupon) -> {
                    ps.setTimestamp(1, valueOf(coupon.getIssuedDate()));
                    ps.setTimestamp(2, valueOf(coupon.getExpirationDate()));
                    ps.setString(3, coupon.getStatus().toString());
                    ps.setLong(4, coupon.getCouponType().getCouponTypeId());
                    ps.setLong(5, coupon.getMember().getCustomerId());
                });
    }
}
