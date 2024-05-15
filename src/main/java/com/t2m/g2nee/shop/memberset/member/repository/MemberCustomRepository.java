package com.t2m.g2nee.shop.memberset.member.repository;

import com.t2m.g2nee.shop.memberset.member.domain.Member;
import java.util.List;
import java.util.Optional;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * MemberRepository의 QueryDSL
 *
 * @author : 정지은
 * @since : 1.0
 */
@NoRepositoryBean
public interface MemberCustomRepository {

    /**
     * ACTIVE 상태의 회원을 찾아 반환하는 메소드
     *
     * @param memberId 찾을 memberId
     * @return Optional<Member> member정보
     */
    Optional<Member> findActiveMemberById(Long memberId);

    /**
     * ACTIVE 상태의 모든회원을 찾아 반환하는 메소드
     *
     * @return List<Member> ACTIVE한 모든 멤버
     */
    List<Member> findActiveAllMemberById();


    /**
     * ACTIVE 상태의 회원을 찾아 반환하는 메소드
     *
     * @param username 찾을 username
     * @return Optional<Member> member정보
     */
    Optional<Member> findActiveMemberByUsername(String username);

}
