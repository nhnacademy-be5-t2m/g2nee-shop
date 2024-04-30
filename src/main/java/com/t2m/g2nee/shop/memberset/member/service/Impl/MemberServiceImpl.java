package com.t2m.g2nee.shop.memberset.member.service.Impl;

import com.t2m.g2nee.shop.exception.AlreadyExistException;
import com.t2m.g2nee.shop.exception.NotFoundException;
import com.t2m.g2nee.shop.memberset.auth.domain.Auth;
import com.t2m.g2nee.shop.memberset.auth.repository.AuthRepository;
import com.t2m.g2nee.shop.memberset.authmember.domain.AuthMember;
import com.t2m.g2nee.shop.memberset.authmember.repository.AuthMemberRepository;
import com.t2m.g2nee.shop.memberset.customers.repository.CustomerRepository;
import com.t2m.g2nee.shop.memberset.grade.domain.Grade;
import com.t2m.g2nee.shop.memberset.grade.repository.GradeRepository;
import com.t2m.g2nee.shop.memberset.member.domain.Member;
import com.t2m.g2nee.shop.memberset.member.dto.request.SignUpMemberRequestDto;
import com.t2m.g2nee.shop.memberset.member.dto.response.MemberDetailInfoResponseDto;
import com.t2m.g2nee.shop.memberset.member.dto.response.MemberResponse;
import com.t2m.g2nee.shop.memberset.member.dto.response.MemberResponseToAuth;
import com.t2m.g2nee.shop.memberset.member.repository.MemberRepository;
import com.t2m.g2nee.shop.memberset.member.service.MemberService;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
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
            authorities.add(
                    String.valueOf(authRepository.findById(authMember.getAuth().getAuthId()).get().getAuthName()));
        }
        return new MemberResponseToAuth(member.getCustomerId(), member.getUsername(), member.getPassword(),
                authorities);
    }

    /**
     * {@inheritDoc}
     *
     * @throws NotFoundException 으로 username 에 해당하는 정보가 없는 경우 예외를 던집니다.
     */
    @Override
    public MemberDetailInfoResponseDto getMemberDetailInfo(Long customerId) {
        if (!memberRepository.existsById(customerId)) {
            throw new NotFoundException(customerId + "의 정보가 존재하지 않습니다.");
        }
        Member member = memberRepository.findById(customerId).get();
        ArrayList<String> authorities = new ArrayList<>();
        List<AuthMember> authMembers = authMemberRepository.getAuthMembersByMember_CustomerId(member.getCustomerId());

        for (AuthMember authMember : authMembers) {
            authorities.add(
                    String.valueOf(authRepository.findById(authMember.getAuth().getAuthId()).get().getAuthName()));
        }

        MemberDetailInfoResponseDto memberDetail = new MemberDetailInfoResponseDto(
                member.getCustomerId(),
                member.getName(),
                member.getUsername(),
                member.getNickname(),
                member.getGender().name(),
                member.getBirthday(),
                member.getPhoneNumber(),
                member.getEmail(),
                authorities
        );
        return memberDetail;
    }

    /**
     * {@inheritDoc}
     *
     * @throws NotFoundException 으로 username 에 해당하는 정보가 없는 경우 예외를 던집니다.
     */
    @Override
    public MemberDetailInfoResponseDto getMemberDetailInfoToAccessToken(String accessToken) {
        Base64.Decoder decoder = Base64.getUrlDecoder();
        String[] access_chunks = accessToken.split("\\.");
        String access_payload = new String(decoder.decode(access_chunks[1]));
        JSONObject aObject = new JSONObject(access_payload);
        String username = aObject.getString("username");

        if (!memberRepository.existsByUsername(username)) {
            throw new NotFoundException(username + "의 정보가 존재하지 않습니다.");
        }
        Member member = (Member) memberRepository.findByUsername(username);
        return getMemberDetailInfo(member.getCustomerId());
    }
}