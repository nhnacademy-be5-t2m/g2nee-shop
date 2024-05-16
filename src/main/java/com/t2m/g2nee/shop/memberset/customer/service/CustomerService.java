package com.t2m.g2nee.shop.memberset.customer.service;

import com.t2m.g2nee.shop.memberset.customer.domain.Customer;
import com.t2m.g2nee.shop.memberset.customer.dto.CustomerSaveRequest;

public interface CustomerService {

    /**
     * 비회원의 정보를 저장하는 메소드
     *
     * @param customerRequest
     * @return 저장된 Customer 정보를 다시 반환
     */
    Customer saveCustomer(CustomerSaveRequest customerRequest);

    /**
     * 고객의 정보를 가져오는 메소드
     *
     * @param customerId
     * @return customer 정보를 반환
     */
    Customer getCustomerInfo(Long customerId);

    /**
     * 비회원의 비밀번호를 가져오는 메소드
     *
     * @param customerId
     * @return 비회원이면 비밀번호, 회원이면 MEMBER, 없는 정보면 null 반환
     */
    String getCustomerPassword(Long customerId);
}
