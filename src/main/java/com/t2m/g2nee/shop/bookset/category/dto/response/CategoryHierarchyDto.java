package com.t2m.g2nee.shop.bookset.category.dto.response;

import com.t2m.g2nee.shop.bookset.category.domain.Category;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
public class CategoryHierarchyDto extends CategoryInfoDto{

    @Setter
    private List<CategoryHierarchyDto> children;

    public CategoryHierarchyDto(CategoryInfoDto categoryInfoDto) {
        super(categoryInfoDto.getCategoryId(), categoryInfoDto.getCategoryName(), categoryInfoDto.getCategoryEngName(), categoryInfoDto.getIsActivated());
    }

    public CategoryHierarchyDto(Category category) {
        super(category);
    }
}
