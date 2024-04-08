package com.t2m.g2nee.shop.memberset.Member.service;

import com.t2m.g2nee.shop.memberset.Member.dto.request.SignUpMemberRequestDto;
import com.t2m.g2nee.shop.memberset.Member.dto.response.MemberResponse;

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
     * login이 가능한 회원인지 확인하는 메소드
     *
     * @param username,password 로그인가능 여부를 확인하는 아이디와 비밀번호
     * @return 로그인가능 여부를 boolean 으로 반환
     */
    boolean login(String username, String password);
}
