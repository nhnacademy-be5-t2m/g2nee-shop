package com.t2m.g2nee.shop.payment.dto.request;

import java.math.BigDecimal;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class TossPaymentRequestDto extends PaymentRequestDto {
    private String paymentKey;

    public TossPaymentRequestDto(String orderId, BigDecimal amount, Long customerId, String paymentKey) {
        super(orderId, amount, customerId, "toss");
        this.paymentKey = paymentKey;
    }

}
