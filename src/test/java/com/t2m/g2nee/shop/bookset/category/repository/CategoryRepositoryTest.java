package com.t2m.g2nee.shop.bookset.category.repository;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import com.t2m.g2nee.shop.bookset.category.domain.Category;
import com.t2m.g2nee.shop.bookset.category.dto.response.CategoryUpdateDto;
import com.t2m.g2nee.shop.bookset.categoryPath.domain.CategoryPath;
import com.t2m.g2nee.shop.bookset.categoryPath.repository.CategoryPathRepository;
import com.t2m.g2nee.shop.config.ElasticsearchConfig;
import com.t2m.g2nee.shop.config.MapperConfig;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

@DataJpaTest
@ActiveProfiles("test")
@TestPropertySource(locations = "classpath:application-test.properties")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(value = {MapperConfig.class, ElasticsearchConfig.class})
class CategoryRepositoryTest {

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    CategoryPathRepository categoryPathRepository;

    Category category1;
    Category category2;

    CategoryPath categoryPath1;
    CategoryPath categoryPath2;
    CategoryPath categoryPath3;

    @BeforeEach
    void setUp() {
        category1 = categoryRepository.save(new Category("테스트카테고리1", "testCategory1", true));//부모
        category2 = categoryRepository.save(new Category("테스트카테고리2", "testCategory2", true));//자식

        categoryPath1 = categoryPathRepository.save(new CategoryPath(category1, category1, 0L));
        categoryPath2 = categoryPathRepository.save(new CategoryPath(category2, category2, 0L));
        categoryPath3 = categoryPathRepository.save(new CategoryPath(category1, category2, 1L));
    }

    @Test
    @DisplayName("existById test")
    void testExistsById() {
        assertTrue(categoryRepository.existsById(category1.getCategoryId()));

        assertTrue(categoryPathRepository.existsById(categoryPath1.getCategoryPathId()));
    }

    @Test
    @DisplayName("save test")
    void testSave() {
        assertNotNull(categoryRepository.findById(category1.getCategoryId()));

        assertNotNull(categoryPathRepository.findById(categoryPath1.getCategoryPathId()));
    }

    @Test
    @DisplayName("existsByCategoryName test")
    void testExistsByCategoryName() {
        assertTrue(categoryRepository.existsByCategoryName("테스트카테고리1"));
    }

    @Test
    @DisplayName("findAll test")
    void testFindAll() {
        List<Category> categoryPage = categoryRepository.findAll();

        assertThat(categoryPage).isNotNull().contains(category1, category2);
    }

    @Test
    @DisplayName("getSubCategoriesByCategoryId test")
    void testGetSubCategoriesByCategoryId() {
        List<Category> categoryPage =
                categoryRepository.getSubCategoriesByCategoryId(category1.getCategoryId());

        assertThat(categoryPage)
                .isNotNull()
                .contains(category2);
    }

    @Test
    @DisplayName("getRootCategories test")
    void testGetRootCategories() {

        List<Category> category = categoryRepository.getRootCategories();

        assertThat(category).isNotNull()
                .contains(category1);
    }

    @Test
    @DisplayName("getFindAncestorIdsByCategoryId")
    void testGetFindAncestorIdsByCategoryId() {
        List<Category> list = categoryRepository.getFindAncestorIdsByCategoryId(category2.getCategoryId());
        assertEquals(2, list.size());
    }

    @Test
    @DisplayName("existsByAncestorAndDescendant test")
    void testExistsByAncestorAndDescendant() {
        assertTrue(categoryPathRepository.existsByAncestorAndDescendant(categoryPath1.getAncestor(),
                categoryPath1.getDescendant()));
    }

    @Test
    @DisplayName("deleteCategoryPathByAncestorIdAndDescendantId test")
    void testDeleteByAncestorCategoryId() {
        categoryPathRepository.deleteCategoryPathByAncestorIdAndDescendantId(category1.getCategoryId());
        assertFalse(categoryPathRepository.existsById(categoryPath1.getCategoryPathId()));
        assertTrue(categoryPathRepository.existsById(categoryPath2.getCategoryPathId()));
        assertFalse(categoryPathRepository.existsById(categoryPath3.getCategoryPathId()));
    }

    @Test
    @DisplayName("findByCategoryNameContaining test")

    void testfindByCategoryNameContaining() {
        Pageable pageable = Pageable.ofSize(10).withPage(0);

        Page<Category> categoryPage = categoryRepository.findByCategoryNameContaining("테스트", pageable);

        assertThat(categoryPage).isNotNull();
        assertThat(categoryPage.getContent()).contains(category1, category2);
    }

    @Test
    @DisplayName("getExistsByCategoryIdAndisActivated test")
    void testGetExistsByCategoryIdAndisActivated() {
        assertTrue(categoryRepository.getExistsByCategoryIdAndisActivated(category1.getCategoryId(), true));
        assertFalse(categoryRepository.getExistsByCategoryIdAndisActivated(category1.getCategoryId(), false));
    }

    @Test
    @DisplayName("softDeleteByCategoryId test")
    void testSoftDeleteByCategoryId() {
        categoryRepository.softDeleteByCategoryId(category1.getCategoryId());

        Category updatedCategory = categoryRepository.findById(category1.getCategoryId()).orElse(null);

        assertThat(updatedCategory.getIsActivated()).isFalse();
    }

    @Test
    @DisplayName("activeCategoryByCategoryId test")
    void testActiveCategoryByCategoryId() {
        category1 = categoryRepository.save(new Category("테스트카테고리1", "testCategory1", false));

        categoryRepository.activeCategoryByCategoryId(category1.getCategoryId());

        Category updatedCategory = categoryRepository.findById(category1.getCategoryId()).orElse(null);

        assertThat(updatedCategory.getIsActivated()).isTrue();
    }

    @Test
    @DisplayName("getFindByCategoryId test")
    void testGetFindByCategoryId() {
        CategoryUpdateDto category = categoryRepository.getFindByCategoryId(category2.getCategoryId());

        assertEquals(category2.getCategoryId(), category.getCategoryId());
        assertEquals(category2.getCategoryName(), category.getCategoryName());
        assertEquals(category2.getCategoryEngName(), category.getCategoryEngName());
        assertNull(category.getChildren());
        assertEquals(category1.getCategoryId(), category.getAncestorCategoryId());
    }
}