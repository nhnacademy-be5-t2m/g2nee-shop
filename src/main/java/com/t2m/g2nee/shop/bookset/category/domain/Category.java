package com.t2m.g2nee.shop.bookset.category.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 카테고리 엔티티
 *
 * @author : 김수빈
 * @since : 1.0
 */
@Entity
@Table(name = "Categories")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryId;
    private String categoryName;
    private String categoryEngName;

    private Boolean isActivated;

    public Category(String categoryName, String categoryEngName, boolean isActivated) {
        this.categoryName = categoryName;
        this.categoryEngName = categoryEngName;
        this.isActivated = isActivated;
    }
}
