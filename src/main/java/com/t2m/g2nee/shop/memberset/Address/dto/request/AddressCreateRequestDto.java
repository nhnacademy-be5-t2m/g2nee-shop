package com.t2m.g2nee.shop.memberset.Address.dto.request;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 주소등록 시 입력한 정보 request DTO개체.
 *
 * @author : 정지은
 * @since : 1.0
 **/
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddressCreateRequestDto {
    @NotNull(message = "memberId를 입력해주십시오.")
    private Long memberId;
    @NotEmpty(message = "주소별칭을 입력해주십시오.")
    private String alias; //주소별칭
    @Pattern(regexp = "^(\\d{3}-\\d{3}|\\d{5})$", message = "우편번호의 형식이 올바르지 않습니다.")
    private String zipcode; //우편번호
    @NotEmpty(message = "주소를 입력해주십시오.")
    private String address; //주소
    @NotEmpty(message = "상세주소를 입력해주십시오.")
    private String detail; //상세주소

    private Boolean isDefault;
}
