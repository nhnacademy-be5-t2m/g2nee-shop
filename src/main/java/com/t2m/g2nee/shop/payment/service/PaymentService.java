package com.t2m.g2nee.shop.payment.service;

import com.t2m.g2nee.shop.pageUtils.PageResponse;
import com.t2m.g2nee.shop.payment.dto.request.PaymentRequest;
import com.t2m.g2nee.shop.payment.dto.response.PaymentInfoDto;


public interface PaymentService {

    /**
     * 결제를 생성합니다.
     *
     * @param request
     */
    PaymentInfoDto createPayment(PaymentRequest request);

    /**
     * 결제를 취소합니다.
     */
    PaymentInfoDto cancelPayment(Long paymentId);


    /**
     * 주문을통해 하나의 결제 내역을 조회합니다.
     *
     */
    PaymentInfoDto getPayment(Long orderId);


    /**
     * 고객의 결제 내역을 조회합니다.
     *
     * @param customerId
     * @param page
     * @return
     */
    PageResponse<PaymentInfoDto> getPayments(Long customerId, int page);
}
