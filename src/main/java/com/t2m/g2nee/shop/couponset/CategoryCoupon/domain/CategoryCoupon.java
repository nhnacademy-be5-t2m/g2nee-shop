package com.t2m.g2nee.shop.couponset.CategoryCoupon.domain;

import com.t2m.g2nee.shop.bookset.Category.domain.Category;
import com.t2m.g2nee.shop.couponset.CouponType.domain.CouponType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

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
