package com.t2m.g2nee.shop.orderset.order.repository;

import com.t2m.g2nee.shop.orderset.order.domain.Order;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface OrderRepository extends JpaRepository<Order, Long>, OrderCustomRepository {
    List<Order> findByCoupon_CouponIdAndOrderIdNot(Long couponId, Long orderId);

    /**
     * 주문 번호로 주문 조회
     *
     * @param orderNumber 주문 번호
     * @return 주문 정보 반환
     */
    @Query("SELECT o FROM Order o WHERE o.orderNumber = :orderNumber")
    Optional<Order> findByOrderNumber(String orderNumber);

    Page<Order> findByCustomer_CustomerId(Long customerId, Pageable pageable);
}
