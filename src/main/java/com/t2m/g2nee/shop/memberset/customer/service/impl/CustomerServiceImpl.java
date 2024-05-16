package com.t2m.g2nee.shop.memberset.customer.service.impl;

import com.t2m.g2nee.shop.exception.NotFoundException;
import com.t2m.g2nee.shop.memberset.customer.domain.Customer;
import com.t2m.g2nee.shop.memberset.customer.dto.CustomerSaveRequest;
import com.t2m.g2nee.shop.memberset.customer.repository.CustomerRepository;
import com.t2m.g2nee.shop.memberset.customer.service.CustomerService;
import com.t2m.g2nee.shop.memberset.member.service.MemberService;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

/**
 * 회원,비회원을 포함한 정보를 위한 service 입니다.
 *
 * @author : 정지은
 * @since : 1.0
 */
@Slf4j
@Service
@EnableAsync
@RequiredArgsConstructor
@Transactional
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final MemberService memberService;

    /**
     * {@inheritDoc}
     */
    public Customer saveCustomer(CustomerSaveRequest customerRequest) {
        Customer customer =
                new Customer(customerRequest.getEmail(), customerRequest.getName(), customerRequest.getPassword(),
                        customerRequest.getPhoneNumber());
        return customerRepository.save(customer);
    }

    /**
     * {@inheritDoc}
     *
     * @throws NotFoundException customerId에 해당하는 customer 정보가 없는 경우 예외를 던집니다.
     */
    public Customer getCustomerInfo(Long customerId) {
        return customerRepository.findById(customerId)
                .orElseThrow(() -> new NotFoundException(customerId + "의 정보가 없습니다."));
    }

    /**
     * {@inheritDoc}
     *
     * @throws NotFoundException customerId에 해당하는 customer 정보가 없는 경우 예외를 던집니다.
     */
    public String getCustomerPassword(Long customerId) {
        if (!customerRepository.existsById(customerId)) {
            return "NULL";
        } else if (memberService.isMember(customerId)) {
            return "MEMBER";
        } else {
            return customerRepository.findById(customerId).get().getPassword();
        }
    }
}
