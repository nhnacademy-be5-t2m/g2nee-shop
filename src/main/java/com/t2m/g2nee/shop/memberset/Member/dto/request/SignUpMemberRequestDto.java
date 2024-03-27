package com.t2m.g2nee.shop.memberset.Member.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * 일반 회원가입 시 입력한 정보 request DTO개체.
 *
 * @author : 정지은
 * @since : 1.0
 **/
@Getter
@NoArgsConstructor
public class SignUpMemberRequestDto {

    @NotBlank
    @Pattern(regexp="^[a-zA-Z0-9]{4,20}$", message = "영어와 숫자만 사용하여 4-20자의 형식으로 작성하여 주십시오.")
    private String userName;

    @NotBlank
    @Pattern(regexp="^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[~?!@#$%^&*_-]).{10,20}$", message = "영어, 숫자, 특수문자를 포함하여 10-20자의 형식으로 작성하여 주십시오.")
    private String password;

    @NotBlank
    @Pattern(regexp="^[a-zA-Z가-힣]{2,20}$", message = "영어와 한글만 사용하여 2-20자의 형식으로 작성하여 주십시오.")
    private String name;

    @NotBlank
    @Pattern(regexp="^[a-zA-Z0-9가-힣+-\\_.]{2,10}",message = "한글,영어,숫자,'_','.'을 사용한 2-10자의 형식으로 작성하여 주십시오.")
    private String nickName;

    @NotBlank
    @Pattern(regexp = "^[a-zA-Z0-9+-\\_.]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$", message = "올바른 email 형식으로 작성하여 주십시오.")
    private String email;

    @NotBlank
    @Pattern(regexp = "^.*(?=.*\\d)(?=.{11}).*$", message = "'-'를 제외한 올바른 전화번호형식을 입력해 주십시오.")
    private String phoneNumber;

}
