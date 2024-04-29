package com.t2m.g2nee.shop.payment.dto.request;

import java.math.BigDecimal;
import javax.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@Getter
@Setter
public class TossPaymentRequestDto extends PaymentRequest{
    @NotBlank
    private String paymentKey;

    public TossPaymentRequestDto(String orderNumber, BigDecimal amount, Long customerId, String payType, String paymentKey){
        super(orderNumber, amount, customerId, payType);
        this.paymentKey = paymentKey;
    }
}
