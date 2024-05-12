package com.t2m.g2nee.shop.orderset.orderdetail.repository;

import com.t2m.g2nee.shop.orderset.order.domain.Order;
import com.t2m.g2nee.shop.orderset.orderdetail.dto.response.GetOrderDetailResponseDto;
import java.util.List;

/**
 * 주문상세 repository 에서 querydsl 사용가능하게 하는 클래스
 *
 * @author : 박재희
 * @since : 1.0
 */

public interface OrderDetailCustomRepository {


    /**
     * 주문id 기반으로 주문 상세 내역 확인
     *
     * @param orderId 주문 id
     * @return 주문 상세 목록 반환
     */
    List<GetOrderDetailResponseDto> getOrderDetailListByOrderId(Long orderId);

    List<Order> getRemainOrders(Long orderDetailId, Long couponId);
}
