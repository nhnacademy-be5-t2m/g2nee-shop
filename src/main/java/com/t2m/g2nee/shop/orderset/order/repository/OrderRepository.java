package com.t2m.g2nee.shop.orderset.order.repository;

import com.t2m.g2nee.shop.orderset.order.domain.Order;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {

    Optional<Order> findByOrderNumber(String orderNumber);
}