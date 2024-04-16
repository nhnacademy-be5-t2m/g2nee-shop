package com.t2m.g2nee.shop.orderset.packageType.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class PackageInfoDto {
    private Long packageId;
    private String name;
    private int price;
    private Boolean isActivated;
}
