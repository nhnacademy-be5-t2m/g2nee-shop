package com.t2m.g2nee.shop.couponset.coupon.domain;

import com.t2m.g2nee.shop.couponset.coupontype.domain.CouponType;
import com.t2m.g2nee.shop.memberset.member.domain.Member;
import java.time.LocalDateTime;
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

@Entity
@Table(name = "Coupons")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Coupon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long couponId;
    private LocalDateTime issuedDate;
    private LocalDateTime expirationDate;

    @Enumerated(EnumType.STRING)
    private CouponStatus status;


    @ManyToOne
    @JoinColumn(name = "customerId")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "couponTypeId")
    private CouponType couponType;

    public enum CouponStatus {
        USED("사용"), NOTUSED("미사용"), EXPIRED("만료");

        private final String name;

        CouponStatus(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }
}
