package com.t2m.g2nee.shop.bookset.category.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import com.t2m.g2nee.shop.bookset.category.dto.response.CategoryHierarchyDto;
import com.t2m.g2nee.shop.bookset.category.dto.response.CategoryInfoDto;
import com.t2m.g2nee.shop.bookset.category.dto.response.CategoryUpdateDto;
import com.t2m.g2nee.shop.exception.NotFoundException;
import com.t2m.g2nee.shop.pageutils.PageResponse;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CategoryQueryServiceTest {

    @Autowired
    CategoryQueryService categoryQueryService;

    @Test
    @DisplayName("서브카테고리 얻기 테스트")
    void testGetSubCategories() {
        List<CategoryHierarchyDto> subCategories = categoryQueryService.getSubCategories(1L);

        assertThat(subCategories).isNotNull().hasSize(3);
    }

    @Test
    @DisplayName("서브 카테고리 얻기 실패 테스트")
    void testGetSubCategoriesFail() {
        assertThrows(NotFoundException.class, () -> categoryQueryService.getSubCategories(1000000000L));
    }

    @Test
    @DisplayName("특정 카테고리 얻기 테스트: root 아닌 카테고리")
    void testGetCategory() {
        CategoryUpdateDto category = categoryQueryService.getCategory(2L);

        assertEquals(Long.valueOf(2L), category.getCategoryId());
        assertEquals(3L, category.getChildren().get(0).getCategoryId().longValue());
        assertEquals(Long.valueOf(1L), category.getAncestorCategoryId());
    }

    @Test
    @DisplayName("특정 카테고리 얻기 테스트: root인 카테고리")
    void testGetCategory2() {
        CategoryUpdateDto category = categoryQueryService.getCategory(1L);

        assertEquals(Long.valueOf(1L), category.getCategoryId());
        assertEquals(2L, category.getChildren().get(0).getCategoryId().longValue());
        assertEquals(Long.valueOf(0L), category.getAncestorCategoryId());
    }

    @Test
    @DisplayName("특정 카테고리 얻기 실패 테스트")
    void testGetCategoryFail() {
        assertThrows(NotFoundException.class, () -> categoryQueryService.getCategory(1000000000L));
    }

    @Test
    @DisplayName("최상위 카테고리 얻기 테스트")
    void testGetRootCategories() {
        List<CategoryHierarchyDto> rootCategories = categoryQueryService.getRootCategories();

        assertThat(rootCategories).isNotNull();
        assertEquals(11L, rootCategories.get(1).getCategoryId().longValue());
        assertEquals(2L, rootCategories.get(0).getChildren().get(0).getCategoryId().longValue());
        assertEquals(3L,
                rootCategories.get(0).getChildren().get(0).getChildren().get(0).getCategoryId().longValue());
    }

    @Test
    @DisplayName("카테고리 이름으로 얻기 테스트")
    void testGetCategoriesByName() {
        PageResponse<CategoryInfoDto> allCategories = categoryQueryService.getCategoriesByName("경제", 1);
        assertThat(allCategories).isNotNull();
        assertThat(allCategories.getData()).hasSize(4);

    }
}