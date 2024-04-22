package com.t2m.g2nee.shop.bookset.category.dto.response;


import com.t2m.g2nee.shop.bookset.category.domain.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 카테고리 기본 정보를 반환하는 객체
 * @author : 김수빈
 * @since : 1.0
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryInfoDto {

    /**
     * 카테고리 식별자
     */
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
     * 카테고리 활성 정보
     */
    private Boolean isActivated;

    public CategoryInfoDto(Category category) {
        this.categoryId = category.getCategoryId();
        this.categoryName = category.getCategoryName();
        this.categoryEngName = category.getCategoryEngName();
        this.isActivated = category.getIsActivated();
    }

}
