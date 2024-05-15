package com.t2m.g2nee.shop.memberset.member.service;

import com.t2m.g2nee.shop.memberset.member.domain.Member;
import com.t2m.g2nee.shop.memberset.member.dto.request.SignUpMemberRequestDto;
import com.t2m.g2nee.shop.memberset.member.dto.response.MemberDetailInfoResponseDto;
import com.t2m.g2nee.shop.memberset.member.dto.response.MemberResponse;
import com.t2m.g2nee.shop.memberset.member.dto.response.MemberResponseToAuth;
import java.util.List;

/**
 * 회원 정보를 위한 service 입니다.
 *
 * @author : 정지은
 * @since : 1.0
 */

public interface MemberService {

    /**
     * 회원의 정보를 받아 회원가입을 하는 메소드
     *
     * @param signUpDto 회원가입시 받는 입력
     * @return 회원정보 저장 후 기본 회원정보 response 반환
     */
    MemberResponse signUp(SignUpMemberRequestDto signUpDto);

    /**
     * nickname의 중복을 확인하는 메소드
     *
     * @param nickname 중복체크하려는 nickname
     * @return 중복 여부를 boolean 으로 반환
     */
    boolean existsNickname(String nickname);

    /**
     * username의 중복을 확인하는 메소드
     *
     * @param username 중복체크하려는 username
     * @return 중복 여부를 boolean 으로 반환
     */
    boolean existsUsername(String username);


    /**
     * customerId로 회원인지 비회원인지 확인하는 메소드
     *
     * @param customerId
     * @return 회원인지의 여부를 boolean 으로 반환
     */
    boolean isMember(Long customerId);

    /**
     * username 으로 member 의 정보를 가져오는 메소드
     *
     * @param username
     * @return member 의 정보를 반환
     */
    MemberResponseToAuth getMemberInfo(String username);

    /**
     * customerId로 회원의 세부정보를 가져오는 메소드
     *
     * @param customerId
     * @return 회원의 세부정보를 반환
     */
    MemberDetailInfoResponseDto getMemberDetailInfo(Long customerId);

    /**
     * accessToken 으로 회원의 세부정보를 가져오는 메소드
     *
     * @param accessToken
     * @return 회원의 세부정보를 반환
     */
    MemberDetailInfoResponseDto getMemberDetailInfoToAccessToken(String accessToken);


    /**
     * 모든 멤버를 가져오는 메소드
     *
     * @return List<Member> 모든 멤버 리스트
     * @author : 김수빈
     */
    List<Member> getAllMembers();

    /**
     * 한 회원을 가져오는 메소드
     *
     * @param customerId 고객 id
     * @return Member객체
     * @author : 김수빈
     */
    Member getMember(Long customerId);

    /**
     * 회원의 상태를 변경하는 메소드
     *
     * @param status   바꿀 status
     * @param memberId 바꿔야할 회원 id
     * @return 바꿔진 Member 객체
     */
    Member modifyStatus(Long memberId, String status);
}
