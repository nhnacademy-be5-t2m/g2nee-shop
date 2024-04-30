package com.t2m.g2nee.shop.payment.controller;

import com.t2m.g2nee.shop.pageutils.PageResponse;
import com.t2m.g2nee.shop.payment.dto.request.PaymentRequest;
import com.t2m.g2nee.shop.payment.dto.response.PaymentInfoDto;
import com.t2m.g2nee.shop.payment.service.PaymentService;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/shop/payments")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping
    public ResponseEntity<PaymentInfoDto> createPayment(@RequestBody @Valid PaymentRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED).contentType(MediaType.APPLICATION_JSON)
                .body(paymentService.createPayment(request));
    }

    @PostMapping("/{paymentId}")
    public ResponseEntity<PaymentInfoDto> cancelPayment(@PathVariable("paymentId") Long paymentId) {

        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON)
                .body(paymentService.cancelPayment(paymentId));
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<PaymentInfoDto> getPayment(@PathVariable("orderId") Long orderId) {

        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON)
                .body(paymentService.getPayment(orderId));
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<PageResponse<PaymentInfoDto>> getPayments(@PathVariable("customerId") Long customerId,
                                                                    @RequestParam int page) {

        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON)
                .body(paymentService.getPayments(customerId, page));
    }


}
