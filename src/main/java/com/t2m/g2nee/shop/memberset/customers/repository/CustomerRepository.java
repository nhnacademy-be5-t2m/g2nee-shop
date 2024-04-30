package com.t2m.g2nee.shop.memberset.customers.repository;

import com.t2m.g2nee.shop.memberset.customers.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

}
