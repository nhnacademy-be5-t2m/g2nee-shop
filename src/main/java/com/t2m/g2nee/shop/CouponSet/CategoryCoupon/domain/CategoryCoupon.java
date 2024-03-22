package com.t2m.g2nee.shop.CouponSet.CategoryCoupon.domain;

import com.t2m.g2nee.shop.BookSet.Category.domain.Category;
import com.t2m.g2nee.shop.CouponSet.CouponType.domain.CouponType;
import lombok.*;
import lombok.experimental.SuperBuilder;
import net.bytebuddy.implementation.bind.annotation.Super;

import javax.persistence.*;

@Entity
@Table(name = "CategoryCoupon")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@DiscriminatorValue("CouponType")
public class CategoryCoupon extends CouponType {


    @ManyToOne
    @JoinColumn(name = "categoryId")
    private Category category;
}
