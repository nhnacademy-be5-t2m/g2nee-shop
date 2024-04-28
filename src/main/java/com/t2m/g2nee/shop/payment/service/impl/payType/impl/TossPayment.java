package com.t2m.g2nee.shop.payment.service.impl.payType.impl;

import com.t2m.g2nee.shop.exception.CustomException;
import com.t2m.g2nee.shop.exception.NotFoundException;
import com.t2m.g2nee.shop.memberset.Customer.domain.Customer;
import com.t2m.g2nee.shop.memberset.Customer.repository.CustomerRepository;
import com.t2m.g2nee.shop.orderset.Order.domain.Order;
import com.t2m.g2nee.shop.orderset.Order.repository.OrderRepository;
import com.t2m.g2nee.shop.payment.domain.Payment;
import com.t2m.g2nee.shop.payment.dto.request.PaymentRequest;
import com.t2m.g2nee.shop.payment.dto.request.PaymentRequestDto;
import com.t2m.g2nee.shop.payment.dto.request.TossPaymentRequestDto;
import com.t2m.g2nee.shop.payment.dto.response.TossPaymentResponseDto;
import com.t2m.g2nee.shop.payment.repository.PaymentRepository;
import com.t2m.g2nee.shop.payment.service.impl.payType.PaymentRequestMethod;
import java.time.LocalDateTime;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@Component
@Slf4j
public class TossPayment implements PaymentRequestMethod {

    private final RestTemplate restTemplate;

    private final OrderRepository orderRepository;

    private final CustomerRepository customerRepository;

    private final PaymentRepository paymentRepository;

    private String secretKey;

    @Value("${toss.api.api.url}")
    private String baseUrl;

    public TossPayment(RestTemplate restTemplate, OrderRepository orderRepository,
                       CustomerRepository customerRepository, PaymentRepository paymentRepository, String secretKey) {
        this.restTemplate = restTemplate;
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
        this.paymentRepository = paymentRepository;
        log.info("secretKey:" + secretKey);
        this.secretKey = secretKey;
    }

    @Override
    public Payment requestCreatePayment(PaymentRequest request) {
        if (request instanceof TossPaymentRequestDto) {
            TossPaymentRequestDto tossRequest = (TossPaymentRequestDto) request; // 형변환
            //주문이 유효한지 확인
            Order order = orderRepository.findByOrderNumber(tossRequest.getOrderNumber())
                    .orElseThrow(() -> new NotFoundException("유효하지 않은 주문입니다."));
            // 1. 주문서 저장된 금액과 실제 요청 금액이 일치하는지
            if (!order.getNetAmount().equals(tossRequest.getAmount())) {
                throw new CustomException(HttpStatus.BAD_REQUEST, "주문 금액이 유효하지 않습니다.");
            }
            //2. 결제 주체가 동일한지
            Customer customer = customerRepository.findById(tossRequest.getCustomerId())
                    .orElseThrow(() -> new NotFoundException("유효하지 않은 고객입니다."));
            if (customer.getCustomerId() != tossRequest.getCustomerId()) {
                throw new CustomException(HttpStatus.BAD_REQUEST, "주문자 정보가 유효하지 않습니다.");
            }


            //toss RequestBody Param
            JSONObject param = new JSONObject();
            param.put("paymentKey", tossRequest.getPaymentKey());
            param.put("orderId", tossRequest.getOrderNumber());
            param.put("amount", tossRequest.getAmount());

            UriComponents url = UriComponentsBuilder.fromUriString(baseUrl)
                    .path("/confirm")
                    .build();

            ResponseEntity<TossPaymentResponseDto> response = restTemplate
                    .exchange(url.toUriString(), HttpMethod.GET,
                            new HttpEntity<>(param, makePaymentHeader()), TossPaymentResponseDto.class);

            TossPaymentResponseDto tossResponse = response.getBody();

            //결제 성공: db에 저장
            if (response.getStatusCode() == HttpStatus.OK) {
                Payment.PayStatus status;
                if (tossResponse.getStatus().equals("DONE")) {
                    status = Payment.PayStatus.COMPLETE;
                } else {
                    status = Payment.PayStatus.ABORTED;
                }

                return paymentRepository.save(
                        new Payment(
                                tossResponse.getTotalAmount(), "toss - " + tossResponse.getMethod(),
                                LocalDateTime.parse(tossResponse.getApprovedAt()), tossResponse.getPaymentKey(),
                                status, customer, order
                        )
                );
            } else {
                throw new CustomException(HttpStatus.BAD_REQUEST, tossResponse.getFailure().getMessage());
            }
        } else {
            throw new IllegalArgumentException("잘못된 요청 타입입니다.");
        }
    }

    @Override
    public HttpEntity<MultiValueMap<String, String>> requestCancelPayment(PaymentRequestDto request) {
        return null;
    }

    private HttpHeaders makePaymentHeader() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));

        httpHeaders.setBasicAuth(secretKey);

        return httpHeaders;
    }


    @Override
    public String getPayType() {
        return "toss";
    }
}

//https://docs.tosspayments.com/reference/using-api/api-keys#api-키-에러
