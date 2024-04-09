package com.t2m.g2nee.shop.memberset.Member.dto.response;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * auth 에게 회원정보를 응답하는 response
 *
 * @author : 정지은
 * @since : 1.0
 */
@Getter
@AllArgsConstructor
public class MemberResponseToAuth {
    private String userName;
    private String name;
    private String nickName;
    private List<String> authorities;
}