package com.t2m.g2nee.shop.couponset.coupontype.dto;

import lombok.*;

import java.math.BigDecimal;

/**
 * 쿠폰에 대한 정보를 반환하는 객체입니다.
 *
 * @author : 김수현
 * @since : 1.0
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class CouponTypeInfoDto {
    /**
     * admin 페이지에서 쿠폰의 모든 정보를 얻기위한 dto
     */


    private Long couponTypeId;

    private String couponTypeName;

    private int period;

    private String type;

    private BigDecimal discount;

    private BigDecimal  minimumOrderAmount;

    private BigDecimal maximumDiscount;

    private Long categoryId;

    private Long bookId;

    private String  status;


}
