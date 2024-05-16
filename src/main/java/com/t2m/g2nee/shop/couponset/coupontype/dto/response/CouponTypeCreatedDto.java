package com.t2m.g2nee.shop.couponset.coupontype.dto.response;

import com.t2m.g2nee.shop.couponset.coupontype.domain.CouponType;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 쿠폰 생성시 front에게 쿠폰 생성 정보를 응답하는 dto
 *
 * @author : 김수현
 * @since : 1.0
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class CouponTypeCreatedDto {

    private Integer period;
    private String name;
    private CouponType.Type type;
    private BigDecimal discount;
    private BigDecimal minimumOrderAmount;
    private BigDecimal maximumDiscount;
    private CouponType.CouponTypeStatus status;

}
