package com.t2m.g2nee.shop.memberset.member.dto.response;

import com.t2m.g2nee.shop.memberset.member.domain.Member;
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
    private String grade;
    private Member.MemberStatus status;
    private List<String> authorities;
//    private List<MemberAddressResponseDto> address;
}