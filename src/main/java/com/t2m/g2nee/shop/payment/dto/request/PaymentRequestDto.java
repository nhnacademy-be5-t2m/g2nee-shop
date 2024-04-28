package com.t2m.g2nee.shop.payment.dto.request;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PaymentRequestDto implements PaymentRequest {

    /**
     * 주문 번호
     */
    private String orderNumber;

    /**
     * 결제 금액
     */
    private BigDecimal amount;

    /**
     * 고객 id
     */
    private Long customerId;

    private String payType;

    @Override
    public String getPayType() {
        return this.payType;
    }
}
