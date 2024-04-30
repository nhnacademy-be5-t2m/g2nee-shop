package com.t2m.g2nee.shop.memberset.authMember.repository;

import com.t2m.g2nee.shop.memberset.authMember.domain.AuthMember;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthMemberRepository extends JpaRepository<AuthMember, Long> {

    List<AuthMember> getAuthMembersByMember_CustomerId(Long memberId);

}
