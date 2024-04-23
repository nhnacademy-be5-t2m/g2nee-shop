package com.t2m.g2nee.shop.orderset.packageType.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 포장지 정보를 반환하기 윈한 객체입니다.
 *
 * @author : 김수빈
 * @since : 1.0
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class PackageInfoDto {
    private Long packageId;
    private String name;
    private int price;
    private Boolean isActivated;
}
