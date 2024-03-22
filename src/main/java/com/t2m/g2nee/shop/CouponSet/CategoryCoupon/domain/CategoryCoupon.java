package com.t2m.g2nee.shop.CouponSet.CategoryCoupon.domain;

import com.t2m.g2nee.shop.BookSet.Category.domain.Category;
import com.t2m.g2nee.shop.CouponSet.CouponType.domain.CouponType;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "CategoryCoupon")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@DiscriminatorValue("CouponType")
public class CategoryCoupon extends CouponType {


    @ManyToOne
    @JoinColumn(name = "categoryId", table = "categories")
    private Category category;
}
