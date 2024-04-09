package com.t2m.g2nee.shop.orderset.orderdetail.repository;

import com.t2m.g2nee.shop.orderset.orderdetail.domain.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 주문 상세를 DB에서 다루기 위한 repository 클래스
 */
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long>, OrderDetailCustomRepository {
}
