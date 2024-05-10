package com.t2m.g2nee.shop.memberset.member.service.impl;

import com.t2m.g2nee.shop.exception.AlreadyExistException;
import com.t2m.g2nee.shop.exception.NotFoundException;
import com.t2m.g2nee.shop.memberset.auth.domain.Auth;
import com.t2m.g2nee.shop.memberset.auth.repository.AuthRepository;
import com.t2m.g2nee.shop.memberset.authmember.domain.AuthMember;
import com.t2m.g2nee.shop.memberset.authmember.repository.AuthMemberRepository;
import com.t2m.g2nee.shop.memberset.customer.repository.CustomerRepository;
import com.t2m.g2nee.shop.memberset.grade.domain.Grade;
import com.t2m.g2nee.shop.memberset.grade.repository.GradeRepository;
import com.t2m.g2nee.shop.memberset.member.domain.Member;
import com.t2m.g2nee.shop.memberset.member.dto.request.SignUpMemberRequestDto;
import com.t2m.g2nee.shop.memberset.member.dto.response.MemberDetailInfoResponseDto;
import com.t2m.g2nee.shop.memberset.member.dto.response.MemberResponse;
import com.t2m.g2nee.shop.memberset.member.dto.response.MemberResponseToAuth;
import com.t2m.g2nee.shop.memberset.member.repository.MemberRepository;
import com.t2m.g2nee.shop.memberset.member.service.MemberService;
import com.t2m.g2nee.shop.point.service.PointService;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;
    private final AuthMemberRepository authMemberRepository;
    private final CustomerRepository customerRepository;
    private final AuthRepository authRepository;
    private final GradeRepository gradeRepository;
    private final PointService pointService;


    /**
     * {@inheritDoc}
     *
     * @throws AlreadyExistException username, nickname 이 중복되는 경우 예외를 던집니다.
     */
    @Override
    @Transactional
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
        Member member = customerRepository.save(memberInfo);
        authMemberRepository.save(new AuthMember(authRepository.findByAuthName(Auth.AuthName.ROLE_MEMBER), member));
        pointService.giveSignUpPoint(member);
        return new MemberResponse(member.getUsername(), member.getName(), member.getNickname(),
                member.getGrade().getGradeName().toString());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public boolean existsNickname(String nickname) {
        return memberRepository.existsByNickname(nickname);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public boolean existsUsername(String username) {
        return memberRepository.existsByUsername(username);
    }

    /**
     * {@inheritDoc}
     *
     * @throws NotFoundException 으로 customerId에 해당하는 정보가 없는 경우 예외를 던집니다.
     */
    public boolean isMember(Long customerId) {
        if (!customerRepository.existsById(customerId)) {
            throw new NotFoundException(customerId + "의 정보가 존재하지 않습니다.");
        } else if (!memberRepository.existsById(customerId)) {
            return false;
        }
        return true;
    }


    /**
     * {@inheritDoc}
     *
     * @throws NotFoundException 으로 username 에 해당하는 정보가 없는 경우 예외를 던집니다.
     */
    @Transactional(readOnly = true)
    public MemberResponseToAuth getMemberInfo(String username) {
        if (!existsUsername(username)) {
            throw new NotFoundException(username + "의 정보가 존재하지 않습니다.");
        }
        Member member = (Member) memberRepository.findByUsername(username);
        ArrayList<String> authorities = new ArrayList<>();
        List<AuthMember> authMembers = authMemberRepository.getAuthMembersByMember_CustomerId(member.getCustomerId());

        for (AuthMember authMember : authMembers) {
            authorities.add(
                    String.valueOf(authRepository.findById(authMember.getAuth().getAuthId())
                            .orElseThrow(() -> new NotFoundException("권한 정보가 없습니다.")).getAuthName()));
        }
        return new MemberResponseToAuth(member.getCustomerId(), member.getUsername(), member.getPassword(),
                authorities);
    }

    /**
     * {@inheritDoc}
     *
     * @throws NotFoundException 으로 customerId 에 해당하는 정보가 없는 경우 예외를 던집니다.
     */
    @Override
    @Transactional(readOnly = true)
    public MemberDetailInfoResponseDto getMemberDetailInfo(Long customerId) {
        Member member = memberRepository.findById(customerId)
                .orElseThrow(() -> new NotFoundException(customerId + "의 정보가 존재하지 않습니다."));
        ArrayList<String> authorities = new ArrayList<>();
        List<AuthMember> authMembers = authMemberRepository.getAuthMembersByMember_CustomerId(member.getCustomerId());

        for (AuthMember authMember : authMembers) {
            authorities.add(
                    String.valueOf(authRepository.findById(authMember.getAuth().getAuthId())
                            .orElseThrow(() -> new NotFoundException("권한 정보를 찾을 수 없습니다.")).getAuthName()));
        }

        return new MemberDetailInfoResponseDto(
                member.getCustomerId(),
                member.getName(),
                member.getUsername(),
                member.getNickname(),
                member.getGender().name(),
                member.getBirthday(),
                member.getPhoneNumber(),
                member.getEmail(),
                member.getGrade().getGradeName().getName(),
                member.getMemberStatus(),
                authorities
        );
    }

    /**
     * {@inheritDoc}
     *
     * @throws NotFoundException 으로 username 에 해당하는 정보가 없는 경우 예외를 던집니다.
     */
    @Override
    @Transactional(readOnly = true)
    public MemberDetailInfoResponseDto getMemberDetailInfoToAccessToken(String accessToken) {
        Base64.Decoder decoder = Base64.getUrlDecoder();
        String[] accessChunks = accessToken.split("\\.");
        String accessPayload = new String(decoder.decode(accessChunks[1]));
        JSONObject aObject = new JSONObject(accessPayload);
        String username = aObject.getString("username");

        if (!memberRepository.existsByUsername(username)) {
            throw new NotFoundException(username + "의 정보가 존재하지 않습니다.");
        }
        Member member = (Member) memberRepository.findByUsername(username);
        return getMemberDetailInfo(member.getCustomerId());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public List<Member> getAllMembers() {
        return memberRepository.findAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public Member getMember(Long customerId) {
        return memberRepository.findById(customerId).orElseThrow(() -> new NotFoundException("존재하지 않는 회원입니다."));
    }
}
