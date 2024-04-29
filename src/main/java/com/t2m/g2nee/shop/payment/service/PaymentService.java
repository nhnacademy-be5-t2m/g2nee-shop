package com.t2m.g2nee.shop.payment.service;

import com.t2m.g2nee.shop.pageUtils.PageResponse;
import com.t2m.g2nee.shop.payment.dto.request.PaymentRequest;
import com.t2m.g2nee.shop.payment.dto.response.PaymentInfoDto;

/**
 * 결제를 위한 서비스의 인터페이스입니다.
 * @author : 김수빈
 * @since : 1.0
 */
public interface PaymentService {

    /**
     * 결제를 생성합니다.
     * @param request 결제 생성 요청
     * @return PaymentInfoDto
     */
    PaymentInfoDto createPayment(PaymentRequest request);

    /**
     * 결제를 취소합니다.
     * @param paymentId 결제를 취소할 결제 id
     * @return PaymentInfoDto
     */
    PaymentInfoDto cancelPayment(Long paymentId);


    /**
     * 주문을통해 하나의 결제 내역을 조회합니다.
     * @param orderId 결제 내역을 조회할 주문 id
     * @return PaymentInfoDto
     */
    PaymentInfoDto getPayment(Long orderId);


    /**
     * 고객의 결제 내역을 조회합니다.
     * @param customerId 조회할 고객 id
     * @param page 페이징 처리
     * @return PageResponse<PaymentInfoDto>
     */
    PageResponse<PaymentInfoDto> getPayments(Long customerId, int page);
}
