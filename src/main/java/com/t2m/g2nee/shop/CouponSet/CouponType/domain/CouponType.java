package com.t2m.g2nee.shop.CouponSet.CouponType.domain;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "CouponTypes")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CouponType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long couponTypeId;
    private String name;
    private Integer period;
    private Type type;
    private Long discount;
    private BigDecimal minimumOrderAmounnt;
    private BigDecimal maximumDiscount;

    public enum Type{
        AMOUNT, PERCENT
    }
}
