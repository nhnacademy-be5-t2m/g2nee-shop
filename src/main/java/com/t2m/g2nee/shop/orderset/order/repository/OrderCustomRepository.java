package com.t2m.g2nee.shop.orderset.order.repository;

import com.t2m.g2nee.shop.orderset.order.domain.Order;
import com.t2m.g2nee.shop.orderset.order.dto.response.GetOrderInfoResponseDto;
import com.t2m.g2nee.shop.orderset.order.dto.response.GetOrderListForAdminResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * query 사용하는 클래스
 *
 * @author 박재희
 * @since 1.0
 */

public interface OrderCustomRepository {

    /**
     * 전체 주문 반환
     *
     * @param pageable paging
     * @return 전체 주문 반환
     */
    Page<GetOrderListForAdminResponseDto> getAllOrderList(Pageable pageable);

    /**
     * 주문 상태별 주문목록 반환
     *
     * @param pageable   paging
     * @param orderState 주문상태
     * @return 상태별 주문목록
     */
    Page<GetOrderListForAdminResponseDto> getOrderListByState(
            Pageable pageable, Order.OrderState orderState);

    /**
     * 회원의 전체 주문 반환.
     *
     * @param pageable paging
     * @param memberId 회원번호
     * @return 회원 전체 주문 반환
     */
    Page<GetOrderInfoResponseDto> getOrderListForMembers(Pageable pageable, Long memberId);


    /**
     * 주문id로 단일 주문 정보 반환(회원용)
     *
     * @param orderId 주문id
     * @return 주문 정보 반환
     */
    GetOrderInfoResponseDto getOrderInfoById(Long orderId);

    /**
     * 주문번호로 주문 정보 반환(비회원용)
     *
     * @param orderNumber 주문번호.
     * @return 주문정보 반환
     */
    GetOrderInfoResponseDto getOrderInfoByOrderNumber(String orderNumber);
}
