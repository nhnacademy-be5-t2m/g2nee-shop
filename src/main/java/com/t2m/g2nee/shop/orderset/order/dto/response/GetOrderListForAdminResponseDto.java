package com.t2m.g2nee.shop.orderset.order.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import com.t2m.g2nee.shop.orderset.order.domain.Order.OrderState;
import com.t2m.g2nee.shop.orderset.orderdetail.dto.response.GetOrderDetailResponseDto;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 도서주문 확인하는 dto
 *
 * @author 박재희
 * @since 1.0
 */

@Getter
@NoArgsConstructor
public class GetOrderListForAdminResponseDto {
    private Long orderId;
    private String customerId;
    private List<GetOrderDetailResponseDto> orderDetailId;
    private Timestamp orderDate;
    private OrderState orderState;
    private BigDecimal orderAmount;

    @QueryProjection
    public GetOrderListForAdminResponseDto(Long orderId, String customerId,
                                           List<GetOrderDetailResponseDto> orderDetailId,
                                           Timestamp orderDate, OrderState orderState, BigDecimal orderAmount) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.orderDetailId = orderDetailId;
        this.orderDate = orderDate;
        this.orderState = orderState;
        this.orderAmount = orderAmount;
    }
}
