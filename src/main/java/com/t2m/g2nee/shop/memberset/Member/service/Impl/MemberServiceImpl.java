package com.t2m.g2nee.shop.memberset.Member.service.Impl;

import com.t2m.g2nee.shop.exception.AlreadyExistException;
import com.t2m.g2nee.shop.exception.BadRequestException;
import com.t2m.g2nee.shop.exception.NotFoundException;
import com.t2m.g2nee.shop.memberset.Auth.domain.Auth;
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
import com.t2m.g2nee.shop.memberset.Member.dto.response.MemberResponseToAuth;
import com.t2m.g2nee.shop.memberset.Member.repository.MemberRepository;
import com.t2m.g2nee.shop.memberset.Member.service.MemberService;
import java.util.ArrayList;
import java.util.List;
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
     * @throws AlreadyExistException username, nickname 이 중복되는 경우 예외를 던집니다.
     */
    @Override
    public MemberResponse signUp(SignUpMemberRequestDto signUpDto) {

        if (existsNickname(signUpDto.getNickName())) {
            throw new AlreadyExistException("중복된 nickname 입니다.");

        }
        if (existsUsername(signUpDto.getUserName())) {
            throw new AlreadyExistException("중복된 username 입니다.");
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
        Member member = (Member) customerRepository.save(memberInfo);
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

    /**
     * {@inheritDoc}
     *
     * @throws NotFoundException   으로 회원아이디도 없는 경우 예외를 던집니다.
     * @throws BadRequestException 으로 비밀번호가 틀린 경우 예외를 던집니다.
     */
    @Override
    public boolean login(String username, String password) {
        if (!existsUsername(username)) {
            throw new NotFoundException(username + "은 존재하지 않는 username 입니다.");
        }
        Customer member = memberRepository.findByUsername(username);
        if (!password.equals(member.getPassword())) {
            throw new BadRequestException(username + "의 비밀번호가 틀렸습니다.");
        }
        return true;
    }

    /**
     * {@inheritDoc}
     *
     * @throws NotFoundException 으로 customerId에 해당하는 정보가 없는 경우 예외를 던집니다.
     */
    public boolean isMember(Long customerId) {
        if (!customerRepository.existsById(customerId)) {
            throw new NotFoundException(customerId + "의 정보가 존재하지 않습니다.");
        }
        if (!memberRepository.existsById(customerId)) {
            return false;
        }
        return true;
    }


    /**
     * {@inheritDoc}
     *
     * @throws NotFoundException 으로 username 에 해당하는 정보가 없는 경우 예외를 던집니다.
     */
    public MemberResponseToAuth getMemberInfo(String username) {
        if (!existsUsername(username)) {
            throw new NotFoundException(username + "의 정보가 존재하지 않습니다.");
        }
        Member member = (Member) memberRepository.findByUsername(username);
        ArrayList<String> authorities = new ArrayList<>();
        List<AuthMember> authMembers = authMemberRepository.getAuthMembersByMember_CustomerId(member.getCustomerId());

        for (AuthMember authMember : authMembers) {
            authorities.add(authRepository.findById(authMember.getAuth().getAuthId()).get().getAuthName().getName());
        }
        return new MemberResponseToAuth(member.getUsername(), member.getName(), member.getNickname(), authorities);
    }

}
