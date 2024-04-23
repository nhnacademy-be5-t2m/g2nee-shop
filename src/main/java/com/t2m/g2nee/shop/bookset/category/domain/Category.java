package com.t2m.g2nee.shop.bookset.category.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Categories의 Entity 입니다.
 *
 * @author : 김수빈
 * @since : 1.0
 */
@Entity
@Table(name = "Categories")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Category {

    /**
     * 카테고리 식별자
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryId;
    /**
     * 카테고리 이름
     */
    private String categoryName;
    /**
     * 카테고리 영문 이름
     */
    private String categoryEngName;
    /**
     * 카테고리 활성화 여부: ture(활성), false(비활성)
     */
    private Boolean isActivated;

    public Category(String categoryName, String categoryEngName, boolean isActivated) {
        this.categoryName = categoryName;
        this.categoryEngName = categoryEngName;
        this.isActivated = isActivated;
    }
}
