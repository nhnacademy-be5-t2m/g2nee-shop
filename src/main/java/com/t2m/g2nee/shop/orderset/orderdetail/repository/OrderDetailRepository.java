package com.t2m.g2nee.shop.orderset.orderdetail.repository;

import com.t2m.g2nee.shop.orderset.orderdetail.domain.OrderDetail;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 주문 상세를 DB에서 다루기 위한 repository 클래스
 */

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long>, OrderDetailCustomRepository {

    /**
     * 요청하는 주문id를 가진 OrderDetail 반환
     *
     * @param orderId
     * @return 주문 상세 목록 반환
     */
    List<OrderDetail> findByOrder_OrderId(Long orderId);

    List<OrderDetail> findByCoupon_CouponIdAndOrderDetailIdNot(Long couponId, Long orderId);

}
