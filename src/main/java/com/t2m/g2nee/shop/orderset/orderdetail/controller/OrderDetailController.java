package com.t2m.g2nee.shop.orderset.orderdetail.controller;

import com.t2m.g2nee.shop.orderset.orderdetail.dto.response.GetOrderDetailResponseDto;
import com.t2m.g2nee.shop.orderset.orderdetail.service.OrderDetailService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 주문 상세 사용하는 컨트롤러
 *
 * @author 박재희
 * @since 1.0
 */
@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("/api/v1/shop/orders")
public class OrderDetailController {
    private final OrderDetailService orderDetailService;

    /**
     * 주문id를 기반으로 주문 상세 내역 반환
     *
     * @param orderId 주문id
     * @return 200, 주문 상세 내역 반환
     */
    @GetMapping("/orderDetails/{orderId}")//order 추가
    public ResponseEntity<List<GetOrderDetailResponseDto>> getOrderDetailList(
            @PathVariable("orderId") Long orderId) {
        List<GetOrderDetailResponseDto> orderDetailResponse = orderDetailService.getOrderDetailListByOrderId(orderId);

        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(orderDetailResponse);
    }

}
