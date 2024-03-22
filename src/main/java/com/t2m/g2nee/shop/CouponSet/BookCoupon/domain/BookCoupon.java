package com.t2m.g2nee.shop.CouponSet.BookCoupon.domain;

import com.t2m.g2nee.shop.BookSet.Book.domain.Book;
import com.t2m.g2nee.shop.CouponSet.CouponType.domain.CouponType;
import com.t2m.g2nee.shop.MemberSet.Member.domain.Member;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "BookCoupon")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@DiscriminatorValue("CouponType")
public class BookCoupon extends CouponType{



    @ManyToOne
    @JoinColumn(name = "bookId")
    private Book book;
}
