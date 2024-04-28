package com.t2m.g2nee.shop.payment.dto.response;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class TossPaymentResponseDto {

    private String paymentKey;
    private String orderId;
    private String orderName;
    private String method;
    private BigDecimal totalAmount;
    private String status;
    private String approvedAt;
    private Cancel cancels;
    private Failure failure;

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    public class Cancel {
        private BigDecimal cancelAmount;
        private String cancelReason;
        private String canceledAt;
        private String cancelStatus;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    public class Failure {
        private String code;
        private String message;
    }
}
