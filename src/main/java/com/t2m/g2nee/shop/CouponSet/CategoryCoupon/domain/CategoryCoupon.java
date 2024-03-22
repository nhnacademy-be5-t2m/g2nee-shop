package com.t2m.g2nee.shop.CouponSet.CategoryCoupon.domain;

import com.t2m.g2nee.shop.BookSet.Book.domain.Book;
import com.t2m.g2nee.shop.BookSet.Categroy.domain.Category;
import com.t2m.g2nee.shop.CouponSet.CouponType.domain.CouponType;
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
public class CategoryCoupon extends CouponType {



    @ManyToOne
    @JoinColumn(name = "categoryId")
    private Category category;
}
