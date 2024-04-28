package com.t2m.g2nee.shop.orderset.Order.repository;

import com.t2m.g2nee.shop.orderset.Order.domain.Order;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {

    Optional<Order> findByOrderNumber(String orderNumber);
}
