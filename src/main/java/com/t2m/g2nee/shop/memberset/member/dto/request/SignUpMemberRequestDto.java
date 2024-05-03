package com.t2m.g2nee.shop.memberset.member.dto.request;

import javax.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 일반 회원가입 시 입력한 정보 request DTO개체.
 *
 * @author : 정지은
 * @since : 1.0
 **/
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SignUpMemberRequestDto {

    @Pattern(regexp = "^[a-zA-Z0-9]{4,20}$|^[a-zA-Z0-9+-\\_.]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$", message = "영어와 숫자만 사용한 4-20자의 형식이나 이메일의 형식으로 작성하여 주십시오.")
    private String userName;

    @Pattern(regexp = "^(?=.*?[A-Za-z])(?=.*?[0-9])(?=.*?[~?!@#$%^&*_-]).{8,20}$", message = "영어 숫자, 특수문자를 포함하여 8-20자의 형식으로 작성하여 주십시오.")
    private String password;

    @Pattern(regexp = "^[a-zA-Z가-힣]{2,20}$", message = "영어와 한글만 사용하여 2-20자의 형식으로 작성하여 주십시오.")
    private String name;

    @Pattern(regexp = "^[a-zA-Z0-9가-힣+-\\_.]{2,10}$", message = "한글,영어,숫자,'_','.'을 사용한 2-10자의 형식으로 작성하여 주십시오.")
    private String nickName;

    @Pattern(regexp = "^[a-zA-Z0-9+-\\_.]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$", message = "올바른 email 형식으로 작성하여 주십시오.")
    private String email;

    @Pattern(regexp = "^\\d{3}-\\d{3,4}-\\d{4}$", message = "'-'를 제외한 올바른 전화번호형식으로 작성하여 주십시오.")
    private String phoneNumber;

    @Pattern(regexp = "^[0-9]{8}$", message = "'-'를 제외한 ex.19990403의 형식으로 작성하여 주십시오.")
    private String birthday;

    @Pattern(regexp = "^Male$|^Female$", message = "'Male','Female' 둘 중에 하나를 작성하여 주십시오.")
    private String gender;

    private Boolean isOAuth;

}
