package com.t2m.g2nee.shop.orderset.order.dto.response;

import com.t2m.g2nee.shop.orderset.orderdetail.dto.response.GetOrderDetailResponseDto;
import java.math.BigDecimal;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class OrderForPaymentDto {
    private String orderName;
    private String orderNumber;
    private BigDecimal amount;
    private Long customerId;
    private List<GetOrderDetailResponseDto> orderDetails;
    private Long orderId;
    private String orderDate;
    private String deliveryWishDate;
    private BigDecimal deliveryFee;
    private String orderState;
    private BigDecimal netAmount;
    private String receiverName;
    private String receiverPhoneNumber;
    private String receiveAddress;
    private String zipcode;
    private String detailAddress;
    private String message;
    private String couponName;
    private String email;
    private String phoneNumber;
    private String name;

}
