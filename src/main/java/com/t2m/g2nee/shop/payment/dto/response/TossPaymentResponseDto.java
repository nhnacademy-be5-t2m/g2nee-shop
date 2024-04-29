package com.t2m.g2nee.shop.payment.dto.response;

import java.math.BigDecimal;
import java.util.List;
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
    private List<Cancel> cancels;
    private Failure failure;

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    public static class Cancel {
        private BigDecimal cancelAmount;
        private String cancelReason;
        private String canceledAt;
        private String cancelStatus;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    public static class Failure {
        private String code;
        private String message;
    }
}
