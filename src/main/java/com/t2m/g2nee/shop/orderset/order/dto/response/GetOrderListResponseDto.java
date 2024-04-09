package com.t2m.g2nee.shop.orderset.order.dto.response;

import com.t2m.g2nee.shop.orderset.order.domain.Order;
import java.sql.Timestamp;
import lombok.Getter;

/**
 * 주문 후 전체 주문 조회하는 dto
 *
 * @author 박재희
 * @since 1.0
 */
@Getter
public class GetOrderListResponseDto {

    private String orderNumber;
    private Order.OrderState orderState;
    private Timestamp orderDate;


}
