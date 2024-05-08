package com.t2m.g2nee.shop.payment.service.impl;

import com.t2m.g2nee.shop.exception.BadRequestException;
import com.t2m.g2nee.shop.exception.CustomException;
import com.t2m.g2nee.shop.exception.NotFoundException;
import com.t2m.g2nee.shop.orderset.order.domain.Order;
import com.t2m.g2nee.shop.orderset.order.service.OrderService;
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
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * PaymentService의 구현체입니다.
 *
 * @author : 김수빈
 * @since : 1.0
 */
@Service
public class PaymentServiceImpl implements PaymentService {

    private static final int MAXPAGEBUTTONS = 5;

    private final PaymentRepository paymentRepository;

    private final OrderDetailService orderDetailService;
    private final OrderService orderService;

    private final PaymentServiceFactory factory;


    /**
     * PaymentServiceImpl의 생성자 입니다.
     *
     * @param paymentRepository  결제 레포지토리
     * @param orderDetailService 주문 상세 서비스
     * @param factory            결제 시, 어떤 pg사의 결제 구현을 사용할 지 정하는 메소드
     */
    public PaymentServiceImpl(PaymentRepository paymentRepository, OrderDetailService orderDetailService,
                              PaymentServiceFactory factory, OrderService orderService) {
        this.paymentRepository = paymentRepository;
        this.orderDetailService = orderDetailService;
        this.factory = factory;
        this.orderService = orderService;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public PaymentInfoDto createPayment(PaymentRequest request) {
        Order order = orderService.getOrder(request.getOrderNumber());

        //주문상태가 결제대기인지 확인
        if (order.getOrderState() == Order.OrderState.PAYWAITING) {

            //결제 승인
            PaymentRequestMethod paymentMethod = factory.getPaymentRequest(request.getPayType());
            Payment payment = paymentMethod.requestCreatePayment(request);

            //결제 실패 경우: 주문 상태 변경
            if (Objects.isNull(payment)) {
                orderService.changeOrderState(order.getOrderId(), Order.OrderState.ABORTED);
                throw new CustomException(HttpStatus.INTERNAL_SERVER_ERROR, "결제에 실패하였습니다.");
            }

            //재고 변경
            orderDetailService.setBookQuantity(order.getOrderId());

            //TODO: 포인트 사용 + 포인트 저장

            //TODO: 쿠폰 사용으로 변경

            //TODO: 결제가 완료되면 장바구니에 같은 물건이 있을 경우 지우기

            //결제 테이블 저장
            return convertToPaymentInfoDto(paymentRepository.save(payment));
        }

        //결제 대기 상태가 아닌 경우 예외
        throw new BadRequestException("결제에 유효한 주문이 아닙니다.");

    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public PaymentInfoDto cancelPayment(Long paymentId) {
        Payment payment =
                paymentRepository.findById(paymentId).orElseThrow(() -> new NotFoundException("결제가 존재하지 않습니다."));
        PaymentRequestMethod paymentMethod = factory.getPaymentRequest(payment.getPayType().split("-")[0].trim());

        //결제 취소
        Payment cancelPayment = paymentMethod.requestCancelPayment(payment);

        //주문 및 주문 상세 결제 취소 상태 변경
        orderService.changeOrderState(cancelPayment.getOrder().getOrderId(), Order.OrderState.CANCEL);
        orderDetailService.cancelAllOrderDetail(cancelPayment.getOrder().getOrderId());

        return convertToPaymentInfoDto(cancelPayment);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public PaymentInfoDto getPayment(Long orderId) {
        return convertToPaymentInfoDto(
                paymentRepository.findByOrder_OrderId(orderId)
                        .orElseThrow(() -> new NotFoundException("결제가 존재하지 않습니다.")));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
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
     *
     * @param payment
     * @return
     */
    private PaymentInfoDto convertToPaymentInfoDto(Payment payment) {

        return new PaymentInfoDto(payment.getPaymentId(), payment.getAmount(), payment.getPayType(),
                payment.getPaymentDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")),
                payment.getPayStatus().getName(), payment.getOrder().getOrderId(),
                payment.getOrder().getOrderNumber(), orderDetailService.getOrderName(payment.getOrder().getOrderId()));
    }
}
