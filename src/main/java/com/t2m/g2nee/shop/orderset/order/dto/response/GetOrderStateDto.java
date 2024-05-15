package com.t2m.g2nee.shop.orderset.order.dto.response;

import com.t2m.g2nee.shop.orderset.order.domain.Order;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class GetOrderStateDto {

    private GetOrderStateDto() {
    }

    @Setter
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class StatusResponse {
        private Order.OrderState orderState;
    }
}
