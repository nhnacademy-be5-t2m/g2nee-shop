package com.t2m.g2nee.shop.payment.repository;

import com.t2m.g2nee.shop.payment.domain.Payment;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 결제 엔티티의 데이터베이스 테이블에 접근하는 메소드를 사용하기 위한 인터페이스입니다.
 * @author : 김수빈
 * @since : 1.0
 */
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    /**
     * 고객 id에 해당하는 결제 정보를 페이징 처리합니다.
     * @param customerId 고객 id
     * @param pageable 페이징 객체
     * @return Page<Payment>
     */
    Page<Payment> findByCustomer_CustomerId(Long customerId, Pageable pageable);

    /**
     * 주문 id에 해당하는 결제 정보를 조회합니다
     * @param orderId 주문 id
     * @return Optional<Payment>
     */
    Optional<Payment> findByOrder_OrderId(Long orderId);
}
