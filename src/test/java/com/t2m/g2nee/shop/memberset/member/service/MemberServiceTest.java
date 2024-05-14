//package com.t2m.g2nee.shop.memberset.member.service;
//
//import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
//import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.anyString;
//import static org.mockito.Mockito.times;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//
//import com.t2m.g2nee.shop.exception.AlreadyExistException;
//import com.t2m.g2nee.shop.memberset.auth.domain.Auth;
//import com.t2m.g2nee.shop.memberset.auth.repository.AuthRepository;
//import com.t2m.g2nee.shop.memberset.authmember.domain.AuthMember;
//import com.t2m.g2nee.shop.memberset.authmember.repository.AuthMemberRepository;
//import com.t2m.g2nee.shop.memberset.customer.repository.CustomerRepository;
//import com.t2m.g2nee.shop.memberset.grade.domain.Grade;
//import com.t2m.g2nee.shop.memberset.grade.repository.GradeRepository;
//import com.t2m.g2nee.shop.memberset.member.domain.Member;
//import com.t2m.g2nee.shop.memberset.member.dto.request.SignUpMemberRequestDto;
//import com.t2m.g2nee.shop.memberset.member.dto.response.MemberResponse;
//import com.t2m.g2nee.shop.memberset.member.repository.MemberRepository;
//import com.t2m.g2nee.shop.memberset.member.service.impl.MemberServiceImpl;
//import com.t2m.g2nee.shop.memberset.member.service.dummy.MemberDummy;
//import com.t2m.g2nee.shop.point.service.PointService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.ArgumentCaptor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.context.annotation.Import;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//
//@ExtendWith(SpringExtension.class)
//@Import(MemberServiceImpl.class)
//class MemberServiceTest {
//
//    @Autowired
//    MemberService memberService;
//    @MockBean
//    MemberRepository memberRepository;
//    @MockBean
//    AuthMemberRepository authMemberRepository;
//    @MockBean
//    CustomerRepository customerRepository;
//    @MockBean
//    AuthRepository authRepository;
//    @MockBean
//    GradeRepository gradeRepository;
//    @MockBean
//    PointService pointService;
//    SignUpMemberRequestDto memberRequestDto;
//    MemberResponse memberResponse;
//    Member member;
//    Grade grade;
//    Auth auth;
//    AuthMember authMember;
//    ArgumentCaptor<Member> captor;
//
//
//    @BeforeEach
//    void setUp() {
//        member = MemberDummy.basicMemberDummy();
//        grade = MemberDummy.normalGradeDummy();
//        auth = MemberDummy.customerAuthDummy();
//        authMember = MemberDummy.authMemberDummy();
//        captor = ArgumentCaptor.forClass(Member.class);
//        memberRequestDto = new SignUpMemberRequestDto(
//                "honggildong",
//                "gildong123@",
//                "홍길동",
//                "홍길동",
//                "gildong@naver.com",
//                "010-1111-1111",
//                "19990203",
//                "Male",
//                false
//        );
//        memberResponse = new MemberResponse(
//                "honggildong",
//                "홍길동",
//                "길동",
//                "NORMAL"
//        );
//
//    }
//
//    @Test
//    @DisplayName("member 생성 성공 테스트")
//    void memberCreateSuccess() {
//        when(memberRepository.existsByNickname(anyString())).thenReturn(false);
//        when(memberRepository.existsByUsername(anyString())).thenReturn(false);
//
//        when(gradeRepository.findByGradeName(any())).thenReturn(grade);
//        when(customerRepository.save(any())).thenReturn(member);
//        when(authRepository.findByAuthName(any())).thenReturn(auth);
//        memberService.signUp(memberRequestDto);
//
//        verify(customerRepository, times(1))
//                .save(captor.capture());
//
//        Member result = captor.getValue();
//        assertThat(memberResponse.getUserName())
//                .isEqualTo(result.getUsername());
//
//    }
//
//    @Test
//    @DisplayName("member 생성 - username 중복 테스트")
//    void memberCreateFail_duplicateUsername() {
//        when(memberRepository.existsByNickname(anyString())).thenReturn(false);
//        when(memberRepository.existsByUsername(anyString())).thenReturn(true);
//        when(gradeRepository.findByGradeName(any())).thenReturn(grade);
//        when(customerRepository.save(any())).thenReturn(member);
//        when(authRepository.findByAuthName(any())).thenReturn(auth);
//        assertThatThrownBy(() -> memberService.signUp(memberRequestDto))
//                .isInstanceOf(AlreadyExistException.class);
//    }
//
//    @Test
//    @DisplayName("member 생성 - nickname 중복 테스트")
//    void memberCreateFail_duplicateNickname() {
//        when(memberRepository.existsByNickname(anyString())).thenReturn(true);
//        when(memberRepository.existsByUsername(anyString())).thenReturn(false);
//        assertThatThrownBy(() -> memberService.signUp(memberRequestDto))
//                .isInstanceOf(AlreadyExistException.class);
//    }
//
//}