package com.t2m.g2nee.shop.orderset.orderdetail.repository;

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
     * 주문 상세 정보 확인
     *
     * @param orderDetailId 주문상세 번호.
     * @return 주문 상세 정보 반환
     */
//    Optional<GetOrderDetailResponseDto> getOrderDetailById(Long orderDetailId);

    /**
     * 도서 주문 상세 내역 확인
     *
     * @
     * @
     */
    List<GetOrderDetailResponseDto> getOrderDetailListByOrderId(Long orderId);

}
