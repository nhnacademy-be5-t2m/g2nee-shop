package com.t2m.g2nee.shop.couponset.coupontype.domain;

import java.math.BigDecimal;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn
@Entity
@Table(name = "CouponTypes")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class CouponType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long couponTypeId;
    private String name;
    private Integer period;
    @Enumerated(EnumType.STRING)
    private Type type;
    private BigDecimal discount;
    private BigDecimal minimumOrderAmount;
    private BigDecimal maximumDiscount;
    @Enumerated(EnumType.STRING)
    private CouponTypeStatus status;


    public enum Type {
        AMOUNT("금액쿠폰"), PERCENT("퍼센트쿠폰");
        private final String name;

        Type(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    public enum CouponTypeStatus{
        DELETE("삭제"), BATCH("일괄발급"), INDIVIDUAL("개별발급");
        private final String name;

        CouponTypeStatus(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }
}
