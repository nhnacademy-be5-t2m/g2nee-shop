package com.t2m.g2nee.shop.payment.repository;

import com.t2m.g2nee.shop.payment.domain.Payment;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    boolean existsByOrder_OrderId(Long orderId);

    boolean existsByCustomer_CustomerId(Long customerId);

    Page<Payment> findByCustomer_CustomerId(Long customerId, Pageable pageable);

    Optional<Payment> findByOrder_OrderId(Long orderId);
}
