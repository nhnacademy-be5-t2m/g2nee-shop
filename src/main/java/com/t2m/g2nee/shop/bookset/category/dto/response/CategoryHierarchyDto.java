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

    /**
     * 카테고리 객체를 받아 CategoryHierarchyDto를 초기화하는 생성자입니다.
     *
     * @param category 카테고리 객체
     */
    public CategoryHierarchyDto(Category category) {
        super(category);
    }

    /**
     * CategoryHierarchyDto의 생성자입니다.
     *
     * @param categoryId      카테고리 id
     * @param categoryName    카테고리 이름
     * @param categoryEngName 카테고리 영문 이름
     * @param isActivated     활성화 여부
     */
    public CategoryHierarchyDto(Long categoryId, String categoryName, String categoryEngName, Boolean isActivated) {
        super(categoryId, categoryName, categoryEngName, isActivated);
    }
}
