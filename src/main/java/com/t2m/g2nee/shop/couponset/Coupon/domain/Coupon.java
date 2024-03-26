package com.t2m.g2nee.shop.couponset.Coupon.domain;

import com.t2m.g2nee.shop.couponset.CouponType.domain.CouponType;
import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;

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

    public enum CouponStatus{
        USED, NOTUSED, EXPIRED;
    }
}
