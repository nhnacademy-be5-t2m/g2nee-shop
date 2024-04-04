package com.t2m.g2nee.shop.bookset.category.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;

import com.t2m.g2nee.shop.bookset.category.domain.Category;
import com.t2m.g2nee.shop.bookset.category.dto.request.CategorySaveDto;
import com.t2m.g2nee.shop.bookset.category.dto.request.CategoryUpdateDto;
import com.t2m.g2nee.shop.bookset.category.dto.response.CategoryInfoDto;
import com.t2m.g2nee.shop.bookset.category.repository.CategoryRepository;
import com.t2m.g2nee.shop.exception.AlreadyExistException;
import com.t2m.g2nee.shop.exception.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class CategoryServiceTest {

    @Autowired
    CategoryService categoryService;

    @Autowired
    CategoryQueryService categoryQueryService;

    @Autowired
    CategoryRepository categoryRepository;

    CategoryInfoDto category1;
    CategoryInfoDto category2;

    CategoryInfoDto category3;


    @BeforeEach
    void setUp() {
        category1 = categoryService.saveCategory(new CategorySaveDto("테스트카테고리1", "testCategory1", 0L));//부모
        category2 = categoryService.saveCategory(
                new CategorySaveDto("테스트카테고리2", "testCategory2", category1.getCategoryId()));//자식
        category3 = categoryService.saveCategory(
                new CategorySaveDto("테스트카테고리3", "testCategory3", category1.getCategoryId()));//자식
    }

    @Test
    void testSave() {
        assertNotNull(categoryQueryService.getCategory(category1.getCategoryId()));
        assertNotNull(categoryQueryService.getCategory(category2.getCategoryId()));
        assertThat(category1.getCategoryName()).isEqualTo("테스트카테고리1");
    }

    @Test
    void testSaveFail() {
        assertThrows(AlreadyExistException.class, () -> {
            categoryService.saveCategory(
                    new CategorySaveDto("테스트카테고리1", "testCategory1", 0L)
            );
        });
    }

    @Test
    void testUpdate() {
        CategoryUpdateDto request = new CategoryUpdateDto(category3.getCategoryId(), "테스트카테고리4", "testCategory4",
                category2.getCategoryId());
        category3 = categoryService.updateCategory(request);

        assertEquals(request.getCategoryId(), category3.getCategoryId());
        assertEquals(request.getCategoryName(), category3.getCategoryName());
        assertEquals(request.getCategoryEngName(), category3.getCategoryEngName());
    }

    @Test
    void testUpdateFail() {
        assertThrows(NotFoundException.class, () -> {
            categoryService.updateCategory(
                    new CategoryUpdateDto(1000L, "테스트카테고리1", "testCategory1", 0L)
            );
        });
    }

    @Test
    void testDelete_activeCategory() {
        categoryService.deleteCategory(category1.getCategoryId());

        assertThrows(NotFoundException.class, () -> {
            categoryService.deleteCategory(category1.getCategoryId());
        });
        //지운 걸 다시 활성화, activeCategory에 대한 test 코드
        categoryService.activeCategory(category1.getCategoryId());

        Category category = categoryRepository.findById(category1.getCategoryId()).orElseThrow();
        assertEquals(category.getCategoryId(), category1.getCategoryId());
    }

    @Test
    void testDeleteFail() {
        assertThrows(NotFoundException.class, () -> categoryService.deleteCategory(1000L));
    }

    @Test
    void testActiveCategoryFail() {

        assertThrows(NotFoundException.class, () -> categoryService.activeCategory(1000L));
    }


}