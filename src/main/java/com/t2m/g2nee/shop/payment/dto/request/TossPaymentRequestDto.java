package com.t2m.g2nee.shop.payment.dto.request;

import java.math.BigDecimal;
import javax.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * tossPayment에 사용할 요청 dto입니다.
 * @author : 김수빈
 * @since : 1.0
 */
@NoArgsConstructor
@Getter
@Setter
public class TossPaymentRequestDto extends PaymentRequest{
    /**
     * 페이먼트 키
     */
    @NotBlank
    private String paymentKey;

    /**
     * TossPaymentRequestDto의 생성자 입니다.
     * @param orderNumber 주문 번호
     * @param amount 결제 금액
     * @param customerId 고객 id
     * @param payType 결제 종류
     * @param paymentKey 페이먼트 키
     */
    public TossPaymentRequestDto(String orderNumber, BigDecimal amount, Long customerId, String payType, String paymentKey){
        super(orderNumber, amount, customerId, payType);
        this.paymentKey = paymentKey;
    }
}
