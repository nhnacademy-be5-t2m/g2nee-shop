package com.t2m.g2nee.shop.orderset.order.dto.response;

import com.t2m.g2nee.shop.orderset.order.domain.Order.OrderState;
import java.math.BigDecimal;
import java.sql.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 도서주문 확인하는 dto
 *
 * @author 박재희
 * @since 1.0
 */

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class GetOrderListForAdminResponseDto {
    private Long orderId;
    private String customerId;
    private Timestamp orderDate;
    private OrderState orderState;
    private BigDecimal orderAmount;
    private Boolean isCancelled;
}
