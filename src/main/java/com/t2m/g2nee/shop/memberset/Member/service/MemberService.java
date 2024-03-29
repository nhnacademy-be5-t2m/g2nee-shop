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
}
