package com.t2m.g2nee.shop.payment.service.impl;

import com.t2m.g2nee.shop.payment.service.impl.payType.PaymentRequestMethod;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class PaymentServiceFactory {

    private final Map<String, PaymentRequestMethod> paymentRequestMethodMap = new HashMap<>();

    public PaymentServiceFactory(List<PaymentRequestMethod> paymentRequestMethod) {
        paymentRequestMethod.forEach(method -> paymentRequestMethodMap.put(method.getPayType(), method));
    }

    public PaymentRequestMethod getPaymentRequest(String payType) {
        return paymentRequestMethodMap.get(payType);
    }
}
