package com.t2m.g2nee.shop.CouponSet.BookCoupon.domain;

import com.t2m.g2nee.shop.BookSet.Book.domain.Book;
import com.t2m.g2nee.shop.CouponSet.CouponType.domain.CouponType;
import com.t2m.g2nee.shop.MemberSet.Member.domain.Member;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Entity
@Table(name = "BookCoupon")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@DiscriminatorValue("BookCoupon")
public class BookCoupon extends CouponType{

    @ManyToOne
    @JoinColumn(name = "bookId")
    private Book book;
}
