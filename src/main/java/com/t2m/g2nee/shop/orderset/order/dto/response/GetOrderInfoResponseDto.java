package com.t2m.g2nee.shop.orderset.order.dto.response;

import com.t2m.g2nee.shop.orderset.order.domain.Orders;
import java.math.BigDecimal;
import java.sql.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 주문 상세 정보 조회하는 dto
 *
 * @author 박재희
 * @since 1.0
 */
@Getter
@AllArgsConstructor
public class GetOrderInfoResponseDto {
    private final Long orderId;
    private final String orderNumber;
    private final Timestamp orderDate;
    private final Timestamp deliveryWishDate;
    private final BigDecimal deliveryFee;
    private final Orders.OrderState orderState;
    private final BigDecimal netAmount;
    private final BigDecimal orderAmount;
    private final String receiverName;
    private final String receiverPhoneNumber;
    private final String receiveAddress;
    private final String zipcode;
    private final String detail;
    private final String message;
    private final Long customerId;
    private final Long couponId;

    //todo: 주문 이름 (xxx 외 n 건) 추가
    private String orderName;

    public GetOrderInfoResponseDto(Long orderId, String orderNumber, Timestamp orderDate, Timestamp deliveryWishDate,
                                   BigDecimal deliveryFee, Orders.OrderState orderState, BigDecimal netAmount,
                                   BigDecimal orderAmount, String receiverName, String receiverPhoneNumber,
                                   String receiveAddress, String zipcode, String detail, String message,
                                   Long customerId, Long couponId) {
        this.orderId = orderId;
        this.orderNumber = orderNumber;
        this.orderDate = orderDate;
        this.deliveryWishDate = deliveryWishDate;
        this.deliveryFee = deliveryFee;
        this.orderState = orderState;
        this.netAmount = netAmount;
        this.orderAmount = orderAmount;
        this.receiverName = receiverName;
        this.receiverPhoneNumber = receiverPhoneNumber;
        this.receiveAddress = receiveAddress;
        this.zipcode = zipcode;
        this.detail = detail;
        this.message = message;
        this.customerId = customerId;
        this.couponId = couponId;
    }


}
