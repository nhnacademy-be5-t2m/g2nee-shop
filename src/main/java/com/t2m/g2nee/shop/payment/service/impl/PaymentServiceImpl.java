package com.t2m.g2nee.shop.payment.service.impl;

import com.t2m.g2nee.shop.pageUtils.PageResponse;
import com.t2m.g2nee.shop.payment.domain.Payment;
import com.t2m.g2nee.shop.payment.dto.request.PaymentRequest;
import com.t2m.g2nee.shop.payment.dto.response.PaymentInfoDto;
import com.t2m.g2nee.shop.payment.repository.PaymentRepository;
import com.t2m.g2nee.shop.payment.service.PaymentService;
import com.t2m.g2nee.shop.payment.service.impl.payType.PaymentRequestMethod;
import java.time.format.DateTimeFormatter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;

    private final PaymentServiceFactory factory;

    public PaymentServiceImpl(PaymentRepository paymentRepository, //OrderRepository orderRepository,
                              PaymentServiceFactory factory) {
        this.paymentRepository = paymentRepository;
        //this.orderRepository = orderRepository;
        this.factory = factory;
    }

    @Override
    public PaymentInfoDto createPayment(PaymentRequest request) {
        //결제 요청을 보냄
        PaymentRequestMethod paymentMethod = factory.getPaymentRequest(request.getPayType());
        return convertToPaymentInfoDto(paymentMethod.requestCreatePayment(request));
    }

    @Override
    public void cancelPayment(String orderNumber) {

    }

    @Override
    public PaymentInfoDto getPayment(String orderNumber) {
        return null;
    }

    @Override
    public PageResponse<PaymentInfoDto> getPayments(Long customerId, int page) {
        return null;
    }

    private PaymentInfoDto convertToPaymentInfoDto(Payment payment) {
        return new PaymentInfoDto(payment.getPaymentId(), payment.getAmount(), payment.getPayType(),
                payment.getPaymentDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")),
                payment.getPayStatus().getName(), payment.getOrder().getOrderId(),
                payment.getOrder().getOrderNumber(), "주문 이름");
    }
}
