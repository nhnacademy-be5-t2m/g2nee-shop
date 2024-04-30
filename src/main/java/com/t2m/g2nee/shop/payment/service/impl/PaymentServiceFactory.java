package com.t2m.g2nee.shop.payment.service.impl;

import com.t2m.g2nee.shop.payment.service.impl.paytype.PaymentRequestMethod;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;

/**
 * payType에 따라 결제 구현 방법을 선택하는 클래스 입니다.
 *
 * @author : 김수빈
 * @since : 1.0
 */
@Component
public class PaymentServiceFactory {

    private final Map<String, PaymentRequestMethod> paymentRequestMethodMap = new HashMap<>();

    /**
     * PaymentServiceFactory의 생성자 입니다.
     * PaymentRequestMethod를 상속하는 모든 클래스를 탐색하여 payType, 그 구현체 쌍을 Map에 저장합니다.
     *
     * @param paymentRequestMethod
     */
    public PaymentServiceFactory(List<PaymentRequestMethod> paymentRequestMethod) {
        paymentRequestMethod.forEach(method -> paymentRequestMethodMap.put(method.getPayType(), method));
    }

    /**
     * payType과 일치하는 구현체를 Map에서 찾아 리턴합니다.
     *
     * @param payType 결제 구현 종류
     * @return PaymentRequestMethod 결제 구현 객체
     */
    public PaymentRequestMethod getPaymentRequest(String payType) {
        return paymentRequestMethodMap.get(payType);
    }
}
