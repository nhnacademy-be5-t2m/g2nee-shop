package com.t2m.g2nee.shop.bookset.category.service;

import static org.junit.Assert.assertThrows;

import com.t2m.g2nee.shop.exception.NotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CategoryQueryServiceTest {

    @Autowired
    CategoryQueryService categoryQueryService;


    @Test
    @DisplayName("서브 카테고리 얻기 실패 테스트")
    void testGetSubCategoriesFail() {
        assertThrows(NotFoundException.class, () -> categoryQueryService.getSubCategories(1000000000L));
    }


    @Test
    @DisplayName("특정 카테고리 얻기 실패 테스트")
    void testGetCategoryFail() {
        assertThrows(NotFoundException.class, () -> categoryQueryService.getCategory(1000000000L));
    }


}