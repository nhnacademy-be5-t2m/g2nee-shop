package com.t2m.g2nee.shop.memberset.customers.service;

import com.t2m.g2nee.shop.memberset.customers.domain.Customer;
import com.t2m.g2nee.shop.memberset.customers.dto.CustomerSaveRequest;

public interface CustomerService {

    /**
     * 비회원의 정보를 저장하는 메소드
     *
     * @param customerRequest
     * @return 저장된 Customer 정보를 다시 반환
     */
    Customer saveCustomer(CustomerSaveRequest customerRequest);

    /**
     * 비회원의 정보를 가져오는 메소드
     *
     * @param customerId
     * @return customer 정보를 반환
     */
    Customer getCustomerInfo(Long customerId);

}
