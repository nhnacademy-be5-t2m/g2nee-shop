package com.t2m.g2nee.shop.bookset.category.dto.response;


import com.t2m.g2nee.shop.bookset.category.domain.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
/**
 * 카테고리 정보를 반환하는 dto
 *
 * @author : 김수빈
 * @since : 1.0
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryInfoDto {

    //카테고리 정보
    private Long categoryId;
    private String categoryName;
    private String categoryEngName;

    private Boolean isActivated;

    public CategoryInfoDto(Category category) {
        this.categoryId = category.getCategoryId();
        this.categoryName = category.getCategoryName();
        this.categoryEngName = category.getCategoryEngName();
        this.isActivated = category.isActivated();
    }

}
