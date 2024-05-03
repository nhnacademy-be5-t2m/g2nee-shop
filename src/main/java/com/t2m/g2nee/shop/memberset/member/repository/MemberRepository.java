package com.t2m.g2nee.shop.memberset.member.repository;

import com.t2m.g2nee.shop.memberset.customer.domain.Customer;
import com.t2m.g2nee.shop.memberset.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long>, MemberRepositoryCustom {

    boolean existsByNickname(String nickname);

    boolean existsByUsername(String username);

    Customer findByUsername(String username);


}
