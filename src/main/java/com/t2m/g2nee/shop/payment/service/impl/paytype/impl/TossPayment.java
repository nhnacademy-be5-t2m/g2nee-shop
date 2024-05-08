package com.t2m.g2nee.shop.payment.service.impl.paytype.impl;

import com.t2m.g2nee.shop.exception.CustomException;
import com.t2m.g2nee.shop.exception.NotFoundException;
import com.t2m.g2nee.shop.memberset.customer.domain.Customer;
import com.t2m.g2nee.shop.memberset.customer.repository.CustomerRepository;
import com.t2m.g2nee.shop.orderset.order.domain.Order;
import com.t2m.g2nee.shop.orderset.order.repository.OrderRepository;
import com.t2m.g2nee.shop.payment.domain.Payment;
import com.t2m.g2nee.shop.payment.dto.request.PaymentRequest;
import com.t2m.g2nee.shop.payment.dto.request.TossPaymentRequestDto;
import com.t2m.g2nee.shop.payment.dto.response.TossPaymentResponseDto;
import com.t2m.g2nee.shop.payment.repository.PaymentRepository;
import com.t2m.g2nee.shop.payment.service.impl.paytype.PaymentRequestMethod;
import java.nio.charset.StandardCharsets;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import lombok.SneakyThrows;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * PaymentRequestMethod의 구현체고, Toss Payment의 결제 시스템을 구현한 클래스입니다.
 *
 * @author : 김수빈
 * @since : 1.0
 */
@Component
public class TossPayment implements PaymentRequestMethod {

    private final RestTemplate restTemplate;

    private final OrderRepository orderRepository;

    private final CustomerRepository customerRepository;

    private final PaymentRepository paymentRepository;

    private String secretKey;

    @Value("${toss.api.api.url}")
    private String baseUrl;

    /**
     * TossPayment의 생성자 입니다.
     *
     * @param restTemplate       toss에 api요청을 보낼 restTemplate
     * @param orderRepository    주문 정보를 확인할 레포지토리
     * @param customerRepository 고객 정보를 확인할 레포지토리
     * @param paymentRepository  결제 정보 저장 및 확인을 위한 레포지토리
     * @param secretKey          Toss Payment api 헤더에 들어가야하는 값
     */
    public TossPayment(RestTemplate restTemplate, OrderRepository orderRepository,
                       CustomerRepository customerRepository, PaymentRepository paymentRepository, String secretKey) {
        this.restTemplate = restTemplate;
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
        this.paymentRepository = paymentRepository;
        this.secretKey = secretKey;
    }

    /**
     * {@inheritDoc}
     *
     * @throws NotFoundException        유효하지 않은 주문일 경우
     * @throws CustomException          주문 금액이 주문과 일치하지 않을 경우
     * @throws NotFoundException        주문자 정보가 존재하지 않을 경우
     * @throws CustomException          주문자 정보가 일치하지 않을 경우
     * @throws IllegalArgumentException Toss Payment가 아닐 경우
     */
    @SneakyThrows
    @Override
    public Payment requestCreatePayment(PaymentRequest request) {
        if (request instanceof TossPaymentRequestDto) {
            TossPaymentRequestDto tossRequest = (TossPaymentRequestDto) request;
            //주문이 유효한지 확인
            Order order = orderRepository.findByOrderNumber(tossRequest.getOrderNumber())
                    .orElseThrow(() ->
                            new NotFoundException("유효하지 않은 주문입니다.")
                    );
            // 1. 주문서 저장된 금액과 실제 요청 금액이 일치하는지
            if (!order.getNetAmount().equals(tossRequest.getAmount())) {
                throw new CustomException(HttpStatus.BAD_REQUEST, "주문 금액이 유효하지 않습니다.");
            }
            //2. 결제 주체가 동일한지
            Customer customer = customerRepository.findById(tossRequest.getCustomerId())
                    .orElseThrow(() -> new NotFoundException("유효하지 않은 고객입니다."));
            if (!Objects.equals(customer.getCustomerId(), tossRequest.getCustomerId())) {
                throw new CustomException(HttpStatus.BAD_REQUEST, "주문자 정보가 유효하지 않습니다.");
            }


            //toss RequestBody Param
            JSONObject param = new JSONObject();
            param.put("paymentKey", tossRequest.getPaymentKey());
            param.put("orderId", tossRequest.getOrderNumber());
            param.put("amount", tossRequest.getAmount());

            //결제 승인
            UriComponents url = UriComponentsBuilder.fromUriString(baseUrl)
                    .path("/confirm")
                    .build();

            ResponseEntity<TossPaymentResponseDto> response = restTemplate
                    .exchange(url.toUriString(), HttpMethod.POST,
                            new HttpEntity<>(param.toString().getBytes(StandardCharsets.UTF_8), makePaymentHeader()),
                            TossPaymentResponseDto.class);

            //결제 승인 결과
            TossPaymentResponseDto tossResponse = Optional.ofNullable(response.getBody())
                    .orElseThrow(() -> new CustomException(HttpStatus.INTERNAL_SERVER_ERROR, "tossResponse가 null입니다."));


            if (response.getStatusCode() == HttpStatus.OK) {
                Payment.PayStatus status;

                //결제 성공: db에 저장
                if (Objects.nonNull(tossResponse.getStatus()) && tossResponse.getStatus().equals("DONE")) {
                    status = Payment.PayStatus.COMPLETE;

                    return new Payment(
                            tossResponse.getTotalAmount(), "toss - " + tossResponse.getMethod(),
                            OffsetDateTime.parse(tossResponse.getApprovedAt()).toLocalDateTime(),
                            tossResponse.getPaymentKey(),
                            status, customer, order
                    );

                    //결제 실패
                } else {
                    return null;
                }
            } else {
                throw new CustomException(HttpStatus.BAD_REQUEST, tossResponse.getFailure().getMessage());
            }
        } else {
            throw new IllegalArgumentException("잘못된 요청 타입입니다.");
        }
    }

    /**
     * {@inheritDoc}
     *
     * @throws CustomException 결제 취소가 성공적으로 되지 않았을 때
     */
    @Override
    @SneakyThrows
    public Payment requestCancelPayment(Payment payment) {

        //toss RequestBody Param
        JSONObject param = new JSONObject();
        param.put("cancelReason", "배송 전 고객 변심");

        UriComponents url = UriComponentsBuilder.fromUriString(baseUrl)
                .path("/" + payment.getPaymentKey())
                .path("/cancel")
                .build();

        ResponseEntity<TossPaymentResponseDto> response = restTemplate
                .exchange(url.toUriString(), HttpMethod.POST,
                        new HttpEntity<>(param.toString().getBytes(StandardCharsets.UTF_8), makePaymentHeader()),
                        TossPaymentResponseDto.class);

        TossPaymentResponseDto tossResponse = Optional.ofNullable(response.getBody()).orElseThrow(
                () -> new CustomException(HttpStatus.INTERNAL_SERVER_ERROR, "예상하지 못한 오류가 발새했습니다.")
        );

        //취소 성공후, 저장 상태 변경
        if (response.getStatusCode() == HttpStatus.OK &&
                tossResponse.getCancels().get(0).getCancelStatus().equals("DONE")) {
            Payment updatePayment = payment;
            updatePayment.setCancel(
                    OffsetDateTime.parse(tossResponse.getCancels().get(0).getCanceledAt()).toLocalDateTime());
            return paymentRepository.save(updatePayment);
        } else {
            throw new CustomException(HttpStatus.BAD_REQUEST, tossResponse.getFailure().getMessage());
        }
    }

    /**
     * toss로 api를 보내기 위한 헤더를 생성합니다.
     *
     * @return HttpHeaders
     */
    private HttpHeaders makePaymentHeader() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));
        httpHeaders.setBasicAuth(secretKey);

        return httpHeaders;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getPayType() {
        return "toss";
    }
}