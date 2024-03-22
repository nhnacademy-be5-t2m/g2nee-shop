package com.t2m.g2nee.shop.CouponSet.Coupon.domain;

import com.t2m.g2nee.shop.CouponSet.CouponType.domain.CouponType;
import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "Coupons")
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
    private Status status;

    @ManyToOne
    @JoinColumn(name = "couponTypeId")
    private CouponType couponType;
    //

    public enum Status{
        USED, NOTUSED, EXPIRED;
    }
}
