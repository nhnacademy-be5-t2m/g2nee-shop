<<<<<<<< HEAD:src/main/java/com/t2m/g2nee/shop/memberset/member/dto/response/MemberAddressResponseDto.java
package com.t2m.g2nee.shop.memberset.member.dto.response;
========
package com.t2m.g2nee.shop.memberset.address.dto.response;
>>>>>>>> origin/develop:src/main/java/com/t2m/g2nee/shop/memberset/address/dto/response/AddressResponseDto.java

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AddressResponseDto {
    private Long addressId;
    private String address;
    private String zipcode;
    private String alias;
    private String detail;
    private Boolean isDefault;
}

