package com.t2m.g2nee.shop.bookset.category.dto.response;

import com.t2m.g2nee.shop.bookset.category.domain.Category;
import java.util.List;
import lombok.Getter;
import lombok.Setter;


public class CategoryHierarchyDto extends CategoryInfoDto{

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
