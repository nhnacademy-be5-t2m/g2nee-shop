package com.t2m.g2nee.shop.memberset.Member.dto.response;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MemberDetailInfoResponseDto {
    private Long memberId;
    private String name;
    private String username;
    private String nickname;
    private String gender;
    private String birthday;
    private String phoneNumber;
    private String email;
    private List<String> authorities;
//    private List<MemberAddressResponseDto> address;
}