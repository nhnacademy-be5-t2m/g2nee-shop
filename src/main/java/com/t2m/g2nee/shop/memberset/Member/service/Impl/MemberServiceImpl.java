package com.t2m.g2nee.shop.memberset.Member.service.Impl;

import com.t2m.g2nee.shop.exception.AlreadyExistException;
import com.t2m.g2nee.shop.memberset.Auth.domain.Auth;
import com.t2m.g2nee.shop.memberset.Auth.repository.AuthRepository;
import com.t2m.g2nee.shop.memberset.AuthMember.domain.AuthMember;
import com.t2m.g2nee.shop.memberset.AuthMember.repository.AuthMemberRepository;
import com.t2m.g2nee.shop.memberset.Customer.repository.CustomerRepository;
import com.t2m.g2nee.shop.memberset.Grade.domain.Grade;
import com.t2m.g2nee.shop.memberset.Grade.repository.GradeRepository;
import com.t2m.g2nee.shop.memberset.Member.domain.Member;
import com.t2m.g2nee.shop.memberset.Member.dto.request.SignUpMemberRequestDto;
import com.t2m.g2nee.shop.memberset.Member.dto.response.MemberResponse;
import com.t2m.g2nee.shop.memberset.Member.repository.MemberRepository;
import com.t2m.g2nee.shop.memberset.Member.service.MemberService;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

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
    private final MemberRepository memberRepository;
    private final AuthMemberRepository authMemberRepository;
    private final CustomerRepository customerRepository;
    private final AuthRepository authRepository;
    private final GradeRepository gradeRepository;


    /**
     * {@inheritDoc}
     *
     * @throws AlreadyExistException username, nickname이 중복되는 경우 예외를 던집니다.
     */
    @Override
    public MemberResponse signUp(SignUpMemberRequestDto signUpDto) {

        if (existsNickname(signUpDto.getNickName())) {
            throw new AlreadyExistException("중복된 nickname입니다.");

        }
        if (existsUsername(signUpDto.getUserName())) {
            throw new AlreadyExistException("중복된 username입니다.");
        }

        Grade grade = gradeRepository.findByGradeName(Grade.GradeName.NORMAL);
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
                .gender(Member.Gender.valueOf(signUpDto.getGender()))
                .build();
        Member member = customerRepository.save(memberInfo);
        authMemberRepository.save(new AuthMember(authRepository.findByAuthName(Auth.AuthName.ROLE_MEMBER), member));

        return new MemberResponse(member.getUsername(), member.getName(), member.getNickname(),
                member.getGrade().getGradeName().toString());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean existsNickname(String nickname) {
        return memberRepository.existsByNickname(nickname);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean existsUsername(String username) {
        return memberRepository.existsByUsername(username);
    }

}
