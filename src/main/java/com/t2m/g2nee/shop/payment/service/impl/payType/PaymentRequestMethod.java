package com.t2m.g2nee.shop.payment.service.impl.payType;

import com.t2m.g2nee.shop.payment.domain.Payment;
import com.t2m.g2nee.shop.payment.dto.request.PaymentRequest;
import com.t2m.g2nee.shop.payment.dto.request.PaymentRequestDto;
import org.springframework.http.HttpEntity;
import org.springframework.util.MultiValueMap;

public interface PaymentRequestMethod {

    Payment requestCreatePayment(PaymentRequest request);

    HttpEntity<MultiValueMap<String, String>> requestCancelPayment(PaymentRequestDto request);

    String getPayType();
}
