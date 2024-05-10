package com.t2m.g2nee.shop.orderset.orderdetail.repository;

import com.t2m.g2nee.shop.orderset.orderdetail.domain.OrderDetail;
import java.sql.PreparedStatement;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class OrderDetailSaveRepository {

    private final JdbcTemplate jdbcTemplate;


    public OrderDetailSaveRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Transactional
    public void saveAll(List<OrderDetail> orderDetails) {
        String sql =
                "INSERT INTO OrderDetails (isCancelled, price, quantity, bookId, packageTypeId, orderId, couponId) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?)";

        jdbcTemplate.batchUpdate(sql,
                orderDetails,
                orderDetails.size(),
                (PreparedStatement ps, OrderDetail orderDetail) -> {
                    ps.setBoolean(1, orderDetail.getIsCancelled());
                    ps.setBigDecimal(2, orderDetail.getPrice());
                    ps.setInt(3, orderDetail.getQuantity());
                    ps.setLong(4, orderDetail.getBook().getBookId());
                    ps.setLong(5, orderDetail.getPackageType().getPackageId());
                    ps.setLong(6, orderDetail.getOrder().getOrderId());
                    ps.setLong(7, orderDetail.getCoupon().getCouponId());
                });
    }
}
