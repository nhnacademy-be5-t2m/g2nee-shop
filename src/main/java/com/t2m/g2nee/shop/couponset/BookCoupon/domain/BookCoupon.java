package com.t2m.g2nee.shop.couponset.BookCoupon.domain;

import com.t2m.g2nee.shop.bookset.Book.domain.Book;
import com.t2m.g2nee.shop.couponset.CouponType.domain.CouponType;
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
