package com.t2m.g2nee.shop.payment.service.impl;

import com.t2m.g2nee.shop.exception.NotFoundException;
import com.t2m.g2nee.shop.orderset.orderdetail.service.OrderDetailService;
import com.t2m.g2nee.shop.pageUtils.PageResponse;
import com.t2m.g2nee.shop.payment.domain.Payment;
import com.t2m.g2nee.shop.payment.dto.request.PaymentRequest;
import com.t2m.g2nee.shop.payment.dto.response.PaymentInfoDto;
import com.t2m.g2nee.shop.payment.repository.PaymentRepository;
import com.t2m.g2nee.shop.payment.service.PaymentService;
import com.t2m.g2nee.shop.payment.service.impl.paytype.PaymentRequestMethod;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PaymentServiceImpl implements PaymentService {

    private static int maxPageButtons = 5;

    private final PaymentRepository paymentRepository;

    private final OrderDetailService orderDetailService;

    private final PaymentServiceFactory factory;

    public PaymentServiceImpl(PaymentRepository paymentRepository, OrderDetailService orderDetailService,
                              PaymentServiceFactory factory) {
        this.paymentRepository = paymentRepository;
        this.orderDetailService = orderDetailService;
        this.factory = factory;
    }

    @Override
    public PaymentInfoDto createPayment(PaymentRequest request){
        //결제 요청을 보냄
        PaymentRequestMethod paymentMethod = factory.getPaymentRequest(request.getPayType());

        return convertToPaymentInfoDto(paymentMethod.requestCreatePayment(request));
    }

    @Override
    public PaymentInfoDto cancelPayment(Long paymentId) {
        Payment payment = paymentRepository.findById(paymentId).orElseThrow(() -> new NotFoundException("결제가 존재하지 않습니다."));
        PaymentRequestMethod paymentMethod = factory.getPaymentRequest(payment.getPayType().split("-")[0].trim());

        return convertToPaymentInfoDto(paymentMethod.requestCancelPayment(payment));
    }

    @Override
    public PaymentInfoDto getPayment(Long orderId) {
        return convertToPaymentInfoDto(
                paymentRepository.findByOrder_OrderId(orderId).orElseThrow(() -> new NotFoundException("결제가 존재하지 않습니다.")));
    }

    @Override
    public PageResponse<PaymentInfoDto> getPayments(Long customerId, int page) {
        Page<Payment> payments = paymentRepository.findByCustomer_CustomerId(customerId,
                PageRequest.of(page - 1, 10, Sort.by("paymentDate"))
        );

        List<PaymentInfoDto> paymentInfoDtoList = payments
                .stream().map(this::convertToPaymentInfoDto)
                .collect(Collectors.toList());

        int startPage = (int) Math.max(1, payments.getNumber() - Math.floor((double) maxPageButtons / 2));
        int endPage = Math.min(startPage + maxPageButtons - 1, payments.getTotalPages());

        if (endPage - startPage + 1 < maxPageButtons) {
            startPage = Math.max(1, endPage - maxPageButtons + 1);
        }


        return PageResponse.<PaymentInfoDto>builder()
                .data(paymentInfoDtoList)
                .currentPage(page)
                .totalPage(payments.getTotalPages())
                .startPage(startPage)
                .endPage(endPage)
                .totalElements(payments.getTotalElements())
                .build();
    }

    private PaymentInfoDto convertToPaymentInfoDto(Payment payment) {

        return new PaymentInfoDto(payment.getPaymentId(), payment.getAmount(), payment.getPayType(),
                payment.getPaymentDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")),
                payment.getPayStatus().getName(), payment.getOrder().getOrderId(),
                payment.getOrder().getOrderNumber(),orderDetailService.getOrderName(payment.getOrder().getOrderId()));
    }
}
