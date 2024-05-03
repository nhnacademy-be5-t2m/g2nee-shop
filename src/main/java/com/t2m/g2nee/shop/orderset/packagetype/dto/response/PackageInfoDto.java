package com.t2m.g2nee.shop.orderset.packagetype.dto.response;

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

    /**
     * 패키지 id
     */
    private Long packageId;

    /**
     * 패키지 이름
     */
    private String name;

    /**
     * 패키지 가격
     */
    private int price;
    
    /**
     * 패키지 활성 여부
     */
    private Boolean isActivated;

    /**
     * 포장지 이미지 url
     */
    private String url;
}
