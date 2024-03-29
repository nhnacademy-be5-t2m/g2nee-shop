package com.t2m.g2nee.shop.memberset.Member.service;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import com.t2m.g2nee.shop.memberset.Auth.repository.AuthRepository;
import com.t2m.g2nee.shop.memberset.AuthMember.repository.AuthMemberRepository;
import com.t2m.g2nee.shop.memberset.Customer.repository.CustomerRepository;
import com.t2m.g2nee.shop.memberset.Grade.repository.GradeRepository;
import com.t2m.g2nee.shop.memberset.Member.dto.request.SignUpMemberRequestDto;
import com.t2m.g2nee.shop.memberset.Member.dto.response.MemberResponse;
import com.t2m.g2nee.shop.memberset.Member.repository.MemberRepository;
import com.t2m.g2nee.shop.memberset.Member.service.Impl.MemberServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@Import(MemberServiceImpl.class)
class MemberServiceTest {

    @Autowired
    MemberService memberService;
    @MockBean
    MemberRepository memberRepository;
    @MockBean
    AuthMemberRepository authMemberRepository;
    @MockBean
    CustomerRepository customerRepository;
    @MockBean
    AuthRepository authRepository;
    @MockBean
    GradeRepository gradeRepository;

    SignUpMemberRequestDto memberRequestDto;
    MemberResponse memberResponse;

    @BeforeEach
    void setUp() {
        memberRequestDto = new SignUpMemberRequestDto(
                "honggildong",
                "gildong123@",
                "홍길동",
                "홍길동",
                "gildong@naver.com",
                "01011111111",
                "19990203",
                "Male",
                false
        );
        memberResponse = new MemberResponse(
                "honggildong",
                "홍길동",
                "길동",
                "일반"
        );

    }

    @Test
    @DisplayName("member 생성 성공 테스트")
    void memberCreateSuccess() {
        when(memberRepository.existsByNickname(anyString())).thenReturn(true);


    }

    @Test
    @DisplayName("member 생성 - username 중복 테스트")
    void memberCreateFail_duplicateUsername() {
    }

    @Test
    @DisplayName("member 생성 - nickname 중복 테스트")
    void memberCreateFail_duplicateNickname() {
    }
}