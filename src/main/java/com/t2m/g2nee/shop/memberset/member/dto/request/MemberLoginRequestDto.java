package com.t2m.g2nee.shop.memberset.member.dto.request;

import javax.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * login 정보가 담긴 요청
 *
 * @author : 정지은
 * @since : 1.0
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MemberLoginRequestDto {

    @Pattern(regexp = "^[a-zA-Z0-9]{4,20}$|^[a-zA-Z0-9+-\\_.]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$", message = "영어와 숫자만 사용한 4-20자의 형식이나 이메일의 형식으로 작성하여 주십시오.")
    private String username;

    private String password;
}