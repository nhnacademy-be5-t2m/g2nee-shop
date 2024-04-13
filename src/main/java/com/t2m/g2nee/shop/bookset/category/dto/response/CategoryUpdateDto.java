package com.t2m.g2nee.shop.bookset.category.dto.response;


import lombok.Getter;

/**
 * 카테고리 업데이트를 위한 dto
 *
 * @author : 김수빈
 * @since : 1.0
 */
public class CategoryUpdateDto extends CategoryHierarchyDto {

    @Getter
    //틍록할 카테고리의 바로 상위 카테고리Id
    private Long ancestorCategoryId;

    public CategoryUpdateDto(Long categoryId, String categoryName, String categoryEngName, Boolean isActivated,
                             Long ancestorCategoryId) {
        super(categoryId, categoryName, categoryEngName, isActivated);
        this.ancestorCategoryId = ancestorCategoryId;
    }
}
