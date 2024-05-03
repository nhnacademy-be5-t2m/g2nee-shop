package com.t2m.g2nee.shop.memberset.member.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MemberAddressResponseDto {
    private String address;
    private String alias;
    private String detail;
    private boolean isDefault;
    private String zipcode;
}

