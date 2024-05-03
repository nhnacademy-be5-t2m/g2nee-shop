package com.t2m.g2nee.shop.memberset.customer.repository;

import com.t2m.g2nee.shop.memberset.customer.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

}
