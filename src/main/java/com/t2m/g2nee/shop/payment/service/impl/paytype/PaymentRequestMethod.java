package com.t2m.g2nee.shop.payment.service.impl.paytype;

import com.t2m.g2nee.shop.payment.domain.Payment;
import com.t2m.g2nee.shop.payment.dto.request.PaymentRequest;

public interface PaymentRequestMethod {

    Payment requestCreatePayment(PaymentRequest request);

    Payment requestCancelPayment(Payment payment);

    String getPayType();
}
