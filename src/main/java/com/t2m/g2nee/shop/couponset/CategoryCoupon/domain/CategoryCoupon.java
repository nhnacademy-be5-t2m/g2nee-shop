package com.t2m.g2nee.shop.couponset.CategoryCoupon.domain;

import com.t2m.g2nee.shop.bookset.Category.domain.Category;
import com.t2m.g2nee.shop.couponset.CouponType.domain.CouponType;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Entity
@Table(name = "CategoryCoupon")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@DiscriminatorValue("CategoryCoupon")
public class CategoryCoupon extends CouponType {


    @ManyToOne
    @JoinColumn(name = "categoryId")
    private Category category;
}
