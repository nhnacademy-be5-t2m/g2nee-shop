
//package com.t2m.g2nee.shop.bookset.category.service;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertThrows;
//
//import com.t2m.g2nee.shop.bookset.category.dto.response.CategoryHierarchyDto;
//import com.t2m.g2nee.shop.bookset.category.dto.response.CategoryInfoDto;
//import com.t2m.g2nee.shop.bookset.category.dto.response.CategoryUpdateDto;
//import com.t2m.g2nee.shop.exception.NotFoundException;
//import com.t2m.g2nee.shop.pageUtils.PageResponse;
//import java.util.List;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//@SpringBootTest
//class CategoryQueryServiceTest {
//
//    @Autowired
//    CategoryQueryService categoryQueryService;
//
//    @Test
//    void testGetSubCategories() {
//        List<CategoryHierarchyDto> subCategories = categoryQueryService.getSubCategories(1L);
//
//        assertThat(subCategories).isNotNull().hasSize(3);
//    }
//
//    @Test
//    void testGetSubCategoriesFail() {
//        assertThrows(NotFoundException.class, () -> categoryQueryService.getSubCategories(1000000000L));
//    }
//
//    @Test
//    void testGetCategory() {
//        CategoryUpdateDto category = categoryQueryService.getCategory(2L);
//
//        assertEquals(Long.valueOf(2L), category.getCategoryId());
//        assertEquals(3L, category.getChildren().get(0).getCategoryId().longValue());
//        assertEquals(Long.valueOf(1L), category.getAncestorCategoryId());
//    }
//
//    @Test
//    void testGetCategory2() {
//        CategoryUpdateDto category = categoryQueryService.getCategory(1L);
//
//        assertEquals(Long.valueOf(1L), category.getCategoryId());
//        assertEquals(2L, category.getChildren().get(0).getCategoryId().longValue());
//        assertEquals(Long.valueOf(0L), category.getAncestorCategoryId());
//    }
//
//    @Test
//    void testGetCategoryFail() {
//        assertThrows(NotFoundException.class, () -> categoryQueryService.getCategory(1000000000L));
//    }
//
//    @Test
//    void testGetRootCategories() {
//        List<CategoryHierarchyDto> rootCategories = categoryQueryService.getRootCategories();
//
//        assertThat(rootCategories).isNotNull();
//        assertEquals(2L, rootCategories.get(0).getChildren().get(0).getCategoryId().longValue());
//        assertEquals(3L,
//                rootCategories.get(0).getChildren().get(0).getChildren().get(0).getCategoryId().longValue());
//    }
//
//    @Test
//    void testGetAllCategories() {
//
//        List<CategoryInfoDto> allCategories = categoryQueryService.getAllCategories();
//
//        assertThat(allCategories).isNotNull().hasSizeGreaterThan(9);
//
//    }
//
//    @Test
//    void testGetCategoriesByName() {
//        PageResponse<CategoryInfoDto> allCategories = categoryQueryService.getCategoriesByName("경제", 1);
//        assertThat(allCategories).isNotNull();
//        assertThat(allCategories.getData()).hasSize(4);
//
//    }
//}

