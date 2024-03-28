package com.t2m.g2nee.shop.memberset.Member.service.Impl;

import com.t2m.g2nee.shop.memberset.Auth.repository.AuthRepository;
import com.t2m.g2nee.shop.memberset.AuthMember.domain.AuthMember;
import com.t2m.g2nee.shop.memberset.AuthMember.repository.AuthMemberRepository;
import com.t2m.g2nee.shop.memberset.Customer.domain.Customer;
import com.t2m.g2nee.shop.memberset.Customer.repository.CustomerRepository;
import com.t2m.g2nee.shop.memberset.Grade.domain.Grade;
import com.t2m.g2nee.shop.memberset.Grade.repository.GradeRepository;
import com.t2m.g2nee.shop.memberset.Member.domain.Member;
import com.t2m.g2nee.shop.memberset.Member.dto.request.SignUpMemberRequestDto;
import com.t2m.g2nee.shop.memberset.Member.dto.response.MemberResponse;
import com.t2m.g2nee.shop.memberset.Member.repository.Impl.MemberRepositoryImpl;
import com.t2m.g2nee.shop.memberset.Member.repository.MemberRepository;
import com.t2m.g2nee.shop.memberset.Member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * 회원 정보를 위한 service 입니다.
 *
 * @author : 정지은
 * @since : 1.0
 */
@Slf4j
@Service
@EnableAsync
@RequiredArgsConstructor
@Transactional
public class MemberServiceImpl implements MemberService {
    MemberRepository memberRepository;
    AuthMemberRepository authMemberRepository;
    CustomerRepository customerRepository;
    AuthRepository authRepository;
    GradeRepository gradeRepository;

    /**
     * 회원의 정보를 받아 회원가입을 하는 메소드
     *
     * @param signUpDto 회
     * @return 회원정보 저장 후 기본 회원정보 response 반환
     */
    public MemberResponse signUp(SignUpMemberRequestDto signUpDto){

        if (existsNickname(signUpDto.getNickName())) {
            //TODO : custom exception 으로 받아 처리
        }
        if (existsUsername(signUpDto.getUserName())) {
            //TODO : custom exception 으로 받아 처리
        }

        Grade grade = gradeRepository.findByGradeId(1L);
        Member memberInfo = Member.builder()
                .email(signUpDto.getEmail())
                .name(signUpDto.getName())
                .password(signUpDto.getPassword())
                .phoneNumber(signUpDto.getPhoneNumber())
                .username(signUpDto.getUserName())
                .nickname(signUpDto.getNickName())
                .birthday(signUpDto.getBirthday())
                .grade(grade)
                .isOAuth(signUpDto.getIsOAuth())
                .gender(signUpDto.getGender())
                .build();
        Member member = (Member) customerRepository.save(memberInfo);
        authMemberRepository.save(new AuthMember(authRepository.findByAuthName("회원"),member));

        return new MemberResponse(member.getUsername(),member.getName(),member.getNickname(),member.getGrade().getGradeName().toString());
    }
    /**
     * nickname의 중복을 확인하는 메소드
     *
     * @param nickname 중복체크하려는 nickname
     * @return 중복 여부를 boolean으로 반환
     */
    public boolean existsNickname(String nickname){
        return memberRepository.existsByNickname(nickname);
    }

    /**
     * username의 중복을 확인하는 메소드
     *
     * @param username 중복체크하려는 username
     * @return 중복 여부를 boolean으로 반환
     */
    public boolean existsUsername(String username){
        return memberRepository.existsByNickname(username);
    }

}
