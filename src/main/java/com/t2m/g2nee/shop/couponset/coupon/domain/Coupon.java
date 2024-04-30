package com.t2m.g2nee.shop.couponset.coupon.domain;

import com.t2m.g2nee.shop.couponset.coupontype.domain.CouponType;
import java.sql.Timestamp;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Coupons")
@DiscriminatorColumn
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Coupon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long couponId;
    private Timestamp issuedDate;
    private Timestamp expirationDate;

    @Enumerated(EnumType.STRING)
    private CouponStatus status;

    @ManyToOne
    @JoinColumn(name = "couponTypeId")
    private CouponType couponType;

    public enum CouponStatus {
        USED, NOTUSED, EXPIRED
    }
}
