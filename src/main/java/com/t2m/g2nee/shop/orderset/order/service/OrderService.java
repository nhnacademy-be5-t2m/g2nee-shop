package com.t2m.g2nee.shop.orderset.order.service;

import com.t2m.g2nee.shop.orderset.order.domain.Order;
import com.t2m.g2nee.shop.orderset.order.dto.request.OrderCreateRequestDto;
import com.t2m.g2nee.shop.orderset.order.dto.response.GetOrderInfoResponseDto;
import com.t2m.g2nee.shop.orderset.order.dto.response.GetOrderListForAdminResponseDto;
import com.t2m.g2nee.shop.orderset.order.dto.response.GetOrderListResponseDto;
import com.t2m.g2nee.shop.pageUtils.PageResponse;
import org.springframework.data.domain.Pageable;

/**
 * 주문 서비스
 *
 * @author : 박재희
 * @since : 1.0
 */
public interface OrderService {

    /**
     * 주문 생성
     *
     * @param orderCreateRequestDto 해당 dto 객체
     */
    Long createOrder(OrderCreateRequestDto orderCreateRequestDto);

    /**
     * 전체 주문 조회(admin용)
     *
     * @param pageable paging
     * @return 전체 주문 반환
     */
    PageResponse<GetOrderListForAdminResponseDto> getALlOrderList(Pageable pageable);

    /**
     * 전체 주문 상태에 따라 반환(admin용)
     *
     * @param pageable   paging
     * @param orderState 주문 상태
     * @return 주문 목록
     */
    PageResponse<GetOrderListForAdminResponseDto> getAllOrdersByState(Pageable pageable, Order.OrderState orderState);

    /**
     * 회원의 전체 주문 조회
     *
     * @param pageable   paging.
     * @param customerId 회원번호.
     * @return 회원의 모든 주문 반환
     */
    PageResponse<GetOrderListResponseDto> getOrderListForMembers(Pageable pageable, Long customerId);

    /**
     * 주문 정보 조회
     *
     * @param orderId 주문 id.
     * @return 주문 정보 반환
     */
    GetOrderInfoResponseDto getOrderInfoById(Long orderId);

    /**
     * 비회원 주문 정보 조회(주문 번호로 조회)
     *
     * @param orderNumber 주문번호.
     * @return 주문 정보
     */
    GetOrderInfoResponseDto getOrderInfoByOrderNumber(String orderNumber);

    /**
     * 주문 상태 변경
     *
     * @param
     * @
     */
    void changeOrderState(Long orderId, Order.OrderState orderState);

}
