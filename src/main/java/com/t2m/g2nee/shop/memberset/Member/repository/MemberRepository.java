package com.t2m.g2nee.shop.memberset.Member.repository;

import com.t2m.g2nee.shop.memberset.Member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long>, MemberRepositoryCustom {

    Member save(Member member);

    boolean existsByNickname(String nickname);

    boolean existsByUsername(String username);
}
