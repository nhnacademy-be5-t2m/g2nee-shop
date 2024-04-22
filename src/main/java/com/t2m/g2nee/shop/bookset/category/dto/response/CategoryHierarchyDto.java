package com.t2m.g2nee.shop.bookset.category.dto.response;

import com.t2m.g2nee.shop.bookset.category.domain.Category;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 * 카테고리 정보를 계층적으로 반환하는 객체입니다.
 *
 * @author : 김수빈
 * @since : 1.0
 */
public class CategoryHierarchyDto extends CategoryInfoDto {

    /**
     * 자식 카테고리 목록
     */
    @Getter
    @Setter
    private List<CategoryHierarchyDto> children;


    public CategoryHierarchyDto(Category category) {
        super(category);
    }

    public CategoryHierarchyDto(Long categoryId, String categoryName, String categoryEngName, Boolean isActivated) {
        super(categoryId, categoryName, categoryEngName, isActivated);
    }
}
