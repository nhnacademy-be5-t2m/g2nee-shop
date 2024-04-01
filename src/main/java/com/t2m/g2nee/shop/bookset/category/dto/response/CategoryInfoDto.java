package com.t2m.g2nee.shop.bookset.category.dto.response;


import com.t2m.g2nee.shop.bookset.category.domain.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryInfoDto {

    //카테고리 정보
    private Long categoryId;
    private String categoryName;
    private String categoryEngName;

    public CategoryInfoDto(Category category) {
        this.categoryId = category.getCategoryId();
        this.categoryName = category.getCategoryName();
        this.categoryEngName = category.getCategoryEngName();
    }
}
