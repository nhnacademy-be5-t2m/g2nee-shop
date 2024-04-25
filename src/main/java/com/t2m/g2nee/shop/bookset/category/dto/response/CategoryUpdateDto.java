package com.t2m.g2nee.shop.bookset.category.dto.response;


import lombok.Getter;

/**
 * 카테고리의 상위 카테고리를 포함한 정보를 반환하는 객체입니다
 *
 * @author : 김수빈
 * @since : 1.0
 */
public class CategoryUpdateDto extends CategoryHierarchyDto {

    @Getter
    /**
     * 바로 상위 카테고리 id
     */
    private Long ancestorCategoryId;

    public CategoryUpdateDto(Long categoryId, String categoryName, String categoryEngName, Boolean isActivated,
                             Long ancestorCategoryId) {
        super(categoryId, categoryName, categoryEngName, isActivated);
        this.ancestorCategoryId = ancestorCategoryId;
    }
}
