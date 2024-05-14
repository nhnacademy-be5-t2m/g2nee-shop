package com.t2m.g2nee.shop.orderset.order.repository;

import com.t2m.g2nee.shop.orderset.order.domain.Order;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long>, OrderCustomRepository {
    List<Order> findByCoupon_CouponIdAndOrderIdNot(Long couponId, Long orderId);

    /**
     * 주문 번호로 주문 조회
     *
     * @param orderNumber 주문 번호
     * @return 주문 정보 반환
     */
    Optional<Order> findByOrderNumber(String orderNumber);
}
