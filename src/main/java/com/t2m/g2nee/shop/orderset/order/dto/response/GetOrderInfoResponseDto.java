package com.t2m.g2nee.shop.orderset.order.dto.response;

import com.t2m.g2nee.shop.orderset.order.domain.Order;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 주문 상세 정보 조회하는 dto
 *
 * @author 박재희
 * @since 1.0
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class GetOrderInfoResponseDto {
    private Long orderId;
    private String orderNumber;
    private LocalDateTime orderDate;
    private LocalDateTime deliveryWishDate;
    //todo: 배송완료날짜 추가해야 함
    private BigDecimal deliveryFee;
    private Order.OrderState orderState;
    private BigDecimal orderAmount;
    private String receiverName;
    private String receiverPhoneNumber;
    private String receiveAddress;
    private String zipcode;
    private String detailAddress;
    private String message;
    private String couponName;


}