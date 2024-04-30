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

    /**
     * CategoryUpdateDto의 생성자 입니다.
     *
     * @param categoryId         카테고리 id
     * @param categoryName       카테고리 이름
     * @param categoryEngName    카테고리 영문이름
     * @param isActivated        카테고리 활성화 여부
     * @param ancestorCategoryId 조상 카테고리 id
     */
    public CategoryUpdateDto(Long categoryId, String categoryName, String categoryEngName, Boolean isActivated,
                             Long ancestorCategoryId) {
        super(categoryId, categoryName, categoryEngName, isActivated);
        this.ancestorCategoryId = ancestorCategoryId;
    }
}
