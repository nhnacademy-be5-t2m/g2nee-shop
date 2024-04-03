package com.t2m.g2nee.shop.bookset.category.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import com.t2m.g2nee.shop.bookset.category.dto.response.CategoryInfoDto;
import com.t2m.g2nee.shop.exception.NotFoundException;
import com.t2m.g2nee.shop.pageUtils.PageResponse;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CategoryQueryServiceTest {

    @Autowired
    CategoryQueryService categoryQueryService;

    @Test
    void testGetSubCategories() {
        PageResponse<CategoryInfoDto> subCategories = categoryQueryService.getSubCategories(1L, 1);

        assertThat(subCategories).isNotNull();
        assertThat(subCategories.getData()).hasSize(3);
    }

    @Test
    void testGetSubCategoriesFail() {
        assertThrows(NotFoundException.class, () -> categoryQueryService.getSubCategories(1000L, 1));
    }

    @Test
    void testGetCategory() {
        CategoryInfoDto category = categoryQueryService.getCategory(1L);

        assertEquals(Long.valueOf(1L), category.getCategoryId());
    }

    @Test
    void testGetCategoryFail() {
        assertThrows(NotFoundException.class, () -> categoryQueryService.getCategory(1000L));
    }

    @Test
    void testGetRootCategories() {
        List<CategoryInfoDto> rootCategories = categoryQueryService.getRootCategories();

        assertThat(rootCategories)
                .isNotNull()
                .hasSize(2);
    }

    @Test
    void testGetAllCategories() {

        PageResponse<CategoryInfoDto> allCategories = categoryQueryService.getAllCategories(1);

        assertThat(allCategories).isNotNull();
        assertThat(allCategories.getData()).hasSize(10);

    }

    @Test
    void testGetCategoriesByName() {
        PageResponse<CategoryInfoDto> allCategories = categoryQueryService.getCategoriesByName("경제", 1);
        assertThat(allCategories).isNotNull();
        assertThat(allCategories.getData()).hasSize(4);

    }
}
