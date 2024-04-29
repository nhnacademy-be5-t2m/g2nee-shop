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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

/**
 * PaymentService의 구현체입니다.
 * @author : 김수빈
 * @since : 1.0
 */
@Service
public class PaymentServiceImpl implements PaymentService {

    private static final int MAXPAGEBUTTONS = 5;

    private final PaymentRepository paymentRepository;

    private final OrderDetailService orderDetailService;

    private final PaymentServiceFactory factory;

    /**
     * PaymentServiceImpl의 생성자 입니다.
     * @param paymentRepository 결제 레포지토리
     * @param orderDetailService 주문 상세 서비스
     * @param factory 결제 시, 어떤 pg사의 결제 구현을 사용할 지 정하는 메소드
     */
    public PaymentServiceImpl(PaymentRepository paymentRepository, OrderDetailService orderDetailService,
                              PaymentServiceFactory factory) {
        this.paymentRepository = paymentRepository;
        this.orderDetailService = orderDetailService;
        this.factory = factory;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PaymentInfoDto createPayment(PaymentRequest request){
        //결제 요청을 보냄
        PaymentRequestMethod paymentMethod = factory.getPaymentRequest(request.getPayType());

        return convertToPaymentInfoDto(paymentMethod.requestCreatePayment(request));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PaymentInfoDto cancelPayment(Long paymentId) {
        Payment payment = paymentRepository.findById(paymentId).orElseThrow(() -> new NotFoundException("결제가 존재하지 않습니다."));
        PaymentRequestMethod paymentMethod = factory.getPaymentRequest(payment.getPayType().split("-")[0].trim());

        return convertToPaymentInfoDto(paymentMethod.requestCancelPayment(payment));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PaymentInfoDto getPayment(Long orderId) {
        return convertToPaymentInfoDto(
                paymentRepository.findByOrder_OrderId(orderId).orElseThrow(() -> new NotFoundException("결제가 존재하지 않습니다.")));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PageResponse<PaymentInfoDto> getPayments(Long customerId, int page) {
        Page<Payment> payments = paymentRepository.findByCustomer_CustomerId(customerId,
                PageRequest.of(page - 1, 10, Sort.by("paymentDate"))
        );

        List<PaymentInfoDto> paymentInfoDtoList = payments
                .stream().map(this::convertToPaymentInfoDto)
                .collect(Collectors.toList());

        int startPage = (int) Math.max(1, payments.getNumber() - Math.floor((double) MAXPAGEBUTTONS / 2));
        int endPage = Math.min(startPage + MAXPAGEBUTTONS - 1, payments.getTotalPages());

        if (endPage - startPage + 1 < MAXPAGEBUTTONS) {
            startPage = Math.max(1, endPage - MAXPAGEBUTTONS + 1);
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

    /**
     * payment 객체를 PaymentInfoDto로 변환합니다.
     * @param payment
     * @return
     */
    private PaymentInfoDto convertToPaymentInfoDto(Payment payment) {

        return new PaymentInfoDto(payment.getPaymentId(), payment.getAmount(), payment.getPayType(),
                payment.getPaymentDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")),
                payment.getPayStatus().getName(), payment.getOrder().getOrderId(),
                payment.getOrder().getOrderNumber(),orderDetailService.getOrderName(payment.getOrder().getOrderId()));
    }
}
