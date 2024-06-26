package com.t2m.g2nee.shop.couponset.bookcoupon.domain;

import com.t2m.g2nee.shop.bookset.book.domain.Book;
import com.t2m.g2nee.shop.couponset.coupontype.domain.CouponType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "BookCoupon")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@DiscriminatorValue("BookCoupon")
public class BookCoupon extends CouponType {

    @ManyToOne
    @JoinColumn(name = "bookId")
    private Book book;

}
