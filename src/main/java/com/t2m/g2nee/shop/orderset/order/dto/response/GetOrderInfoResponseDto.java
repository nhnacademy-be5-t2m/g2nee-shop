package com.t2m.g2nee.shop.orderset.order.dto.response;

import com.t2m.g2nee.shop.orderset.order.domain.Order;
import com.t2m.g2nee.shop.orderset.orderdetail.dto.response.GetOrderDetailResponseDto;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
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
    private Long customerId;
    private String customerName;
    private List<GetOrderDetailResponseDto> orderDetails;
    private LocalDateTime orderDate;
    private LocalDateTime deliveryWishDate;
    private BigDecimal deliveryFee;
    private Order.OrderState orderState;
    private BigDecimal orderAmount;
    private String receiverName;
    private String receiverPhoneNumber;
    private String receiveAddress;
    private String zipcode;
    private String detailAddress;
    private String message;
}
