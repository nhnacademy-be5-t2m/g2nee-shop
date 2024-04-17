package com.t2m.g2nee.shop.orderset.order.dto.response;

import com.t2m.g2nee.shop.orderset.order.domain.Order;
import java.sql.Timestamp;
import lombok.Getter;

/**
 * 주문 상세 정보 조회하는 dto
 *
 * @author 박재희
 * @since 1.0
 */
@Getter
public class GetOrderInfoResponseDto {
    private Long orderId;
    private String orderNumber;
    private Order.OrderState orderState;
    private Timestamp orderDate;


}
