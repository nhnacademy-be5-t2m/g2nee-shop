package com.t2m.g2nee.shop.orderset.order.controller;

import com.t2m.g2nee.shop.orderset.order.domain.Order;
import com.t2m.g2nee.shop.orderset.order.dto.request.OrderSaveDto;
import com.t2m.g2nee.shop.orderset.order.dto.response.GetOrderInfoResponseDto;
import com.t2m.g2nee.shop.orderset.order.dto.response.GetOrderListForAdminResponseDto;
import com.t2m.g2nee.shop.orderset.order.dto.response.OrderForPaymentDto;
import com.t2m.g2nee.shop.orderset.order.service.OrderService;
import com.t2m.g2nee.shop.pageUtils.PageResponse;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 주문 컨트롤러
 *
 * @author 박재희
 * @since 1.0
 */

@RestController
@Validated
@RequestMapping("/api/v1/shop/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    /**
     * admin이 전체 주문을 조회
     *
     * @param page 현재 페이지
     * @return 전체 주문 반환
     */
    @GetMapping("/admin/orders/list")
    public ResponseEntity<PageResponse<GetOrderListForAdminResponseDto>> getAllOrders(
            @RequestParam int page) {
        PageResponse<GetOrderListForAdminResponseDto> adminListResponse = orderService.getALlOrderList(page);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(adminListResponse);
    }


    /**
     * admin이 주문 상태별로 조회
     *
     * @param page       현재 페이지
     * @param orderState 주문 상태
     * @return 주문 list
     */
    @GetMapping("/admin/orders/list/{orderState}")
    public ResponseEntity<PageResponse<GetOrderListForAdminResponseDto>> getAllOrdersByState(
            @RequestParam int page, @PathVariable Order.OrderState orderState) {
        PageResponse<GetOrderListForAdminResponseDto> adminListResponse =
                orderService.getAllOrdersByState(page, orderState);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(adminListResponse);
    }


    /**
     * member가 주문을 조회
     *
     * @param page     현재 page
     * @param memberId 회원번호
     * @return 200, 회원의 전체 주문
     */
    @GetMapping("/members/{memberId}/list")
    public ResponseEntity<PageResponse<OrderForPaymentDto>> getOrderListForMembers(
            @PathVariable("memberId") Long memberId, @RequestParam int page) {
        PageResponse<OrderForPaymentDto> memberListResponse =
                orderService.getOrderListForMembers(page, memberId);

        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(memberListResponse);
    }

    /**
     * 주문id로 주문 정보 조회
     *
     * @param orderId 주문 id
     * @return 200, 주문 정보 반환
     */
    //@MemberAndAuth
    @GetMapping("/order/{orderId}")
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
    @GetMapping("/nonmembers/{orderNumber}")
    public ResponseEntity<GetOrderInfoResponseDto> getOrderInfoByOrderNumber(
            @PathVariable String orderNumber) {
        GetOrderInfoResponseDto customerOrderInfoDto = orderService.getOrderInfoByOrderNumber(orderNumber);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(customerOrderInfoDto);
    }

    /**
     * 주문 상태 변경
     *
     * @param orderId    주문Id
     * @param orderState 주문 상태
     * @return 주문 상태 변경된 주문 정보
     */
    @PatchMapping("/state/{orderId}")
    public ResponseEntity<?> changeOrderState(@PathVariable("orderId") Long orderId,
                                                                    @RequestBody Order.OrderState orderState) {
        orderService.changeOrderState(orderId, orderState);
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .contentType(MediaType.APPLICATION_JSON)
                .build();
    }

    @GetMapping("/orderName/{orderId}")
    public ResponseEntity<String> getOrderName(@PathVariable("orderId") Long orderId){
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(orderService.getOrderName(orderId));
    }


    @PostMapping
    public ResponseEntity<OrderForPaymentDto> createOrder(@RequestBody @Valid OrderSaveDto request) {
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(orderService.saveOrder(request));
    }

    @PostMapping("/existsOrder")
    public ResponseEntity<Boolean> existsOrderNumber(@RequestBody String orderNumber){
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(orderService.existsOrderNumber(orderNumber));
    }
}