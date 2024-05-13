package com.t2m.g2nee.shop.memberset.customer.controller;

import com.t2m.g2nee.shop.memberset.customer.domain.Customer;
import com.t2m.g2nee.shop.memberset.customer.dto.CustomerSaveRequest;
import com.t2m.g2nee.shop.memberset.customer.service.CustomerService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 회원,비회원의 공통 정보를 다루는 컨트롤러 입니다.
 *
 * @author : 정지은
 * @since : 1.0
 */
@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/shop/customer")
public class CustomerController {

    private final CustomerService customerService;

    /**
     * 비회원의 정보를 저장하는 메소드
     *
     * @param customerRequest 비회원의 정보를 입력
     * @return 비회원정보 저장 후 저장된 정보 반환
     */
    @PostMapping("/save")
    public ResponseEntity<Long> customerSave(@Valid @RequestBody CustomerSaveRequest customerRequest) {
        Customer customer = customerService.saveCustomer(customerRequest);
        return ResponseEntity.status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(customer.getCustomerId());

    }

    /**
     * customer 의 정보를 가져오는 메소드
     *
     * @param customerId 가져오고 싶은 customerId 입력
     * @return customerId에 해당하는 customer 정보 반환
     */
    @GetMapping("/getInfo/{customerId}")
    public ResponseEntity<Customer> getCustomersInfo(@PathVariable("customerId") Long customerId) {
        Customer customer = customerService.getCustomerInfo(customerId);

        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(customer);
    }
}
