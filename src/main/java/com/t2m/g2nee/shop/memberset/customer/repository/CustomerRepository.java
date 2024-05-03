package com.t2m.g2nee.shop.memberset.customer.repository;

import com.t2m.g2nee.shop.memberset.customer.domain.Customer;
import com.t2m.g2nee.shop.memberset.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

<<<<<<<HEAD
=======

    Member save(Member member);


>>>>>>>e4150c2d01f76e3dc85e30d7aa58aab9aa7f444e
}
