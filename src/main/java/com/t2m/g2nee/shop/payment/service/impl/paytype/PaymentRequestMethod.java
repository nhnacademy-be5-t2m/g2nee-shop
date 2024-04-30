package com.t2m.g2nee.shop.payment.service.impl.paytype;

import com.t2m.g2nee.shop.payment.domain.Payment;
import com.t2m.g2nee.shop.payment.dto.request.PaymentRequest;

/**
 * 결제시 필요한 공통 메소드를 모은 인터페이스입니다.
 * @author : 김수빈
 * @since : 1.0
 */
public interface PaymentRequestMethod {

    /**
     * 결제 승인을 합니다.
     * @param request 결체 승인 요청 객체
     * @return Payment
     */
    Payment requestCreatePayment(PaymentRequest request);

    /**
     * 결제를 취소합니다.
     * @param payment 취소할 payment 객체
     * @return payment
     */
    Payment requestCancelPayment(Payment payment);

    /**
     * 결제 구현의 payType를 리턴합니다.
     * @return 각 구현체에 해당하는 payType
     */
    String getPayType();
}
