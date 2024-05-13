package com.t2m.g2nee.shop.couponset.coupon.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class CouponDownloadDto extends CouponIssueDto {
    private Long customerId;
}
