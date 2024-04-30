package com.t2m.g2nee.shop.memberset.member.service.dummy;

import static com.t2m.g2nee.shop.memberset.auth.domain.Auth.AuthName.ROLE_MEMBER;
import static com.t2m.g2nee.shop.memberset.grade.domain.Grade.GradeName.NORMAL;
import static com.t2m.g2nee.shop.memberset.member.domain.Member.Gender.Male;

import com.t2m.g2nee.shop.memberset.auth.domain.Auth;
import com.t2m.g2nee.shop.memberset.authMember.domain.AuthMember;
import com.t2m.g2nee.shop.memberset.grade.domain.Grade;
import com.t2m.g2nee.shop.memberset.member.domain.Member;

public class MemberDummy {
    public static Member basicMemberDummy() {
        return Member.builder()
                .email("test@naver.com")
                .name("testName")
                .password("test@1234")
                .phoneNumber("010-1111-1111")
                .username("testId")
                .nickname("testNickname")
                .birthday("20000101")
                .grade(normalGradeDummy())
                .gender(Male)
                .isOAuth(false)
                .build();
    }

    public static Grade normalGradeDummy() {
        return Grade.builder()
                .gradeName(NORMAL)
                .build();
    }

    public static Auth customerAuthDummy() {
        return Auth.builder()
                .authName(ROLE_MEMBER)
                .build();
    }

    public static AuthMember authMemberDummy() {
        return AuthMember.builder()
                .auth(customerAuthDummy())
                .member(basicMemberDummy())
                .build();
    }
}
