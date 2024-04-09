package com.t2m.g2nee.shop.memberset.Member.dto.request;

import javax.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * username 에 대한 정보를 받아오는 요청
 *
 * @author : 정지은
 * @since : 1.0
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UsernameRequestDto {

    @Pattern(regexp = "^[a-zA-Z0-9]{4,20}$|^[a-zA-Z0-9+-\\_.]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$", message = "영어와 숫자만 사용한 4-20자의 형식이나 이메일의 형식으로 작성하여 주십시오.")
    private String username;
}
