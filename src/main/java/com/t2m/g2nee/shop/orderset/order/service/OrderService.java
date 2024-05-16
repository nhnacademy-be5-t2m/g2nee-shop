package com.t2m.g2nee.shop.orderset.order.service;

import com.t2m.g2nee.shop.orderset.order.domain.Order;
import com.t2m.g2nee.shop.orderset.order.dto.request.OrderSaveDto;
import com.t2m.g2nee.shop.orderset.order.dto.response.GetOrderInfoResponseDto;
import com.t2m.g2nee.shop.orderset.order.dto.response.GetOrderListForAdminResponseDto;
import com.t2m.g2nee.shop.orderset.order.dto.response.OrderForPaymentDto;
import com.t2m.g2nee.shop.pageUtils.PageResponse;

/**
 * 주문 서비스
 *
 * @author : 박재희
 * @since : 1.0
 */
public interface OrderService {
    /**
     * 전체 주문 조회(admin용)
     *
     * @param page current page
     * @return 전체 주문 반환
     */
    PageResponse<GetOrderListForAdminResponseDto> getALlOrderList(int page);

    /**
     * 전체 주문 상태에 따라 반환(admin용)
     *
     * @param page       현재 페이지
     * @param orderState 주문 상태
     * @return 주문 목록
     */
    PageResponse<GetOrderListForAdminResponseDto> getAllOrdersByState(int page, Order.OrderState orderState);


    /**
     * 회원의 전체 주문 조회
     *
     * @param page     현재 페이지.
     * @param memberId 회원번호.
     * @return 회원의 모든 주문 반환
     */
    PageResponse<OrderForPaymentDto> getOrderListForMembers(int page, Long memberId);

    /**
     * 주문 정보 조회
     *
     * @param orderId 주문 id.
     * @return 주문 정보 반환
     */
    GetOrderInfoResponseDto getOrderInfoById(Long orderId);

    /**
     * 주문 번호로 조회
     *
     * @param orderNumber 주문번호.
     * @return 주문 정보
     */
    GetOrderInfoResponseDto getOrderInfoByOrderNumber(String orderNumber);

    /**
     * 주문 상태 변경
     *
     * @param orderId    주문 id
     * @param orderState 주문 상태
     */
    void changeOrderState(Long orderId, Order.OrderState orderState);

    /**
     * 주문 soft delete
     *
     * @param orderId 주문Id
     */
    void deleteOrder(Long orderId);

    /**
     * 주문서를 저장합니다.
     *
     * @param orderSaveDto 회원 주문 저장 dto
     * @return 주문 내역
     * @author : 김수빈
     * @since : 1.0
     */
    OrderForPaymentDto saveOrder(OrderSaveDto orderSaveDto);

    /**
     * 주문 번호를 통해 주문을 조회합니다.
     *
     * @param orderNumber 주문 번호
     * @return Order 객체
     * @author : 김수빈
     * @since : 1.0
     */
    Order getOrder(String orderNumber);

    void applyUseCoupon(Order order);

    String getOrderName(Long orderId);

    /**
     * 모든 회원의 등급을 update하는 메소드
     */
    void updateGrade();
}
