package com.t2m.g2nee.shop.orderset.order.controller;

import com.t2m.g2nee.shop.orderset.order.domain.Order;
import com.t2m.g2nee.shop.orderset.order.dto.request.OrderCreateRequestDto;
import com.t2m.g2nee.shop.orderset.order.dto.response.GetOrderInfoResponseDto;
import com.t2m.g2nee.shop.orderset.order.dto.response.GetOrderListForAdminResponseDto;
import com.t2m.g2nee.shop.orderset.order.dto.response.GetOrderListResponseDto;
import com.t2m.g2nee.shop.orderset.order.service.OrderService;
import com.t2m.g2nee.shop.pageUtils.PageResponse;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 주문 컨트롤러
 *
 * @author 박재희
 * @since 1.0
 */

@RestController
@Validated
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    /**
     * 주문을 생성
     *
     * @param createRequest 주문 생성을 위한 dto
     * @return 201 반환
     */
    @PostMapping("/api/orders")
    public ResponseEntity<Long> createOrder(@Valid @RequestBody OrderCreateRequestDto createRequest) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(orderService.createOrder(createRequest));
    }

    /**
     * admin이 전체 주문을 조회
     *
     * @param pageable paging
     * @return 200, 전체 주문 반환
     */
    @GetMapping("/token/orders")
    public ResponseEntity<PageResponse<GetOrderListForAdminResponseDto>> getAllOrders(
            Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(orderService.getALlOrderList(pageable));

    }

    /**
     * admin이 주문 상태별로 조회
     *
     * @param pageable   paging
     * @param orderState 주문 상태
     * @return 주문 list
     */
    public ResponseEntity<PageResponse<GetOrderListForAdminResponseDto>> getAllOrdersByState(
            Pageable pageable, @PathVariable Order.OrderState orderState) {
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(orderService.getAllOrdersByState(pageable, orderState));
    }

    /**
     * member가 주문을 조회
     *
     * @param pageable   paging
     * @param customerId 회원번호
     * @return 200, 회원의 전체 주문
     */
    @GetMapping("/token/orders/members/{customerId}/")
    public ResponseEntity<PageResponse<GetOrderListResponseDto>> getOrderListForMembers(
            Pageable pageable, @PathVariable("customerId") Long customerId) {
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(orderService.getOrderListForMembers(pageable, customerId));
    }

    /**
     * 주문id로 주문 정보 조회(회원용)
     *
     * @param orderId
     * @return
     */
    //@MemberAndAuth
    @GetMapping("/token/orders/{orderId}/members/{customerId}")
    public ResponseEntity<GetOrderInfoResponseDto> getOrderInfoByOrderId(
            @PathVariable Long orderId) {
        GetOrderInfoResponseDto orderInfoResponseDto = orderService.getOrderInfoById(orderId);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(orderInfoResponseDto);

    }

    /**
     * 주문 번호로 주문 정보 조회(비회원용)
     *
     * @param orderNumber 주문 번호
     * @return 200, 주문정보 dto
     */
    @GetMapping("/api/orders/nonmembers/{orderNumber}")
    public ResponseEntity<GetOrderInfoResponseDto> getOrderInfoByOrderNumber(
            @PathVariable String orderNumber) {
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(orderService.getOrderInfoByOrderNumber(orderNumber));
    }
}
