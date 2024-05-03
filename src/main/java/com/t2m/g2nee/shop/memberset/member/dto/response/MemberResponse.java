package com.t2m.g2nee.shop.memberset.member.dto.response;


import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * front에게 기본 회원정보로 응답하는 response
 *
 * @author : 정지은
 * @since : 1.0
 */
@Getter
@AllArgsConstructor
public class MemberResponse {
    private String userName;
    private String name;
    private String nickName;
    private String grade;

}
