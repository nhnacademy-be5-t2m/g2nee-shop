package com.t2m.g2nee.shop.orderset.order.dto.request;

import com.t2m.g2nee.shop.orderset.order.domain.Order;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 전체 주문 생성하는 dto
 *
 * @author 박재희
 * @since 1.0
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderCreateRequestDto {
    private Long orderId;
    private String orderNumber;
    private LocalDateTime orderDate;
    private LocalDateTime deliveryWishDate;
    private BigDecimal deliveryFee;
    private Order.OrderState orderState;
    private BigDecimal netAmount;
    private BigDecimal orderAmount;
    private String receiverName;
    private String receiverPhoneNumber;
    private String receiveAddress;
    private String zipcode;
    private String detail;
    private String message;
    private Long customerId;
    private Long couponId;

}
