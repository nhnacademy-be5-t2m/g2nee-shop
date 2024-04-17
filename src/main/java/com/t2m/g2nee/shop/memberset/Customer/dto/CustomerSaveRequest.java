package com.t2m.g2nee.shop.memberset.Customer.dto;

import javax.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 비회원의 정보만 저장하기 위한 요청
 *
 * @author : 정지은
 * @since : 1.0
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerSaveRequest {

    @Pattern(regexp = "^[a-zA-Z가-힣]{2,20}$", message = "영어와 한글만 사용하여 2-20자의 형식으로 작성하여 주십시오.")
    private String name;

    @Pattern(regexp = "^(?=.*?[A-Za-z])(?=.*?[0-9])(?=.*?[~?!@#$%^&*_-]).{8,20}$", message = "영어 숫자, 특수문자를 포함하여 8-20자의 형식으로 작성하여 주십시오.")
    private String password;

    @Pattern(regexp = "^[a-zA-Z0-9+-\\_.]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$", message = "올바른 email 형식으로 작성하여 주십시오.")
    private String email;

    @Pattern(regexp = "^\\d{3}-\\d{3,4}-\\d{4}$", message = "'-'를 제외한 올바른 전화번호형식으로 작성하여 주십시오.")
    private String phoneNumber;

}
