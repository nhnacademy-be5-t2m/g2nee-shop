package com.t2m.g2nee.shop.payment.service.impl.paytype.impl;

import com.t2m.g2nee.shop.memberset.customer.domain.Customer;
import com.t2m.g2nee.shop.orderset.order.domain.Order;
import com.t2m.g2nee.shop.payment.domain.Payment;
import com.t2m.g2nee.shop.payment.dto.request.PaymentRequest;
import com.t2m.g2nee.shop.payment.repository.PaymentRepository;
import com.t2m.g2nee.shop.payment.service.impl.paytype.PaymentRequestMethod;
import com.t2m.g2nee.shop.point.service.PointService;
import java.time.LocalDateTime;
import org.springframework.stereotype.Component;

@Component
public class PointPayment implements PaymentRequestMethod {

    private final PaymentRepository paymentRepository;

    private final PointService pointService;

    public PointPayment(PaymentRepository paymentRepository, PointService pointService) {
        this.paymentRepository = paymentRepository;
        this.pointService = pointService;
    }

    @Override
    public Payment requestCreatePayment(PaymentRequest request, Customer customer, Order order) {

        return new Payment(
                request.getAmount(), "point - 포인트결제",
                LocalDateTime.now(), null,
                Payment.PayStatus.COMPLETE, customer, order
        );
    }

    @Override
    public Payment requestCancelPayment(Payment payment) {
        Payment updatePayment = payment;
        updatePayment.setCancel(LocalDateTime.now());
        pointService.returnPoint(payment.getOrder().getOrderId());
        return paymentRepository.save(updatePayment);
    }

    @Override
    public String getPayType() {
        return "point";
    }
}
