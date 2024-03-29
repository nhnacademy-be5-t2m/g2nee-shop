package com.t2m.g2nee.shop.memberset.Customer.repository;

import com.t2m.g2nee.shop.memberset.Customer.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository<T extends Customer> extends JpaRepository<T, Long> {


}
