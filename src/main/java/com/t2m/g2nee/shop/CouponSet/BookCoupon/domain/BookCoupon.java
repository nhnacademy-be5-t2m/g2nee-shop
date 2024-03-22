package com.t2m.g2nee.shop.CouponSet.BookCoupon.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "BookCoupon")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookCoupon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

}
