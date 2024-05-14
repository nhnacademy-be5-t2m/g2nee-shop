package com.t2m.g2nee.shop.bookset.category.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.t2m.g2nee.shop.bookset.category.repository.CategoryRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CategoryTest {

    @Autowired
    CategoryRepository categoryRepository;

    @Test
    @DisplayName("Category Entity test")
    void test() {
        Category category = new Category("테스트카테고리", "testCategory", true);

        categoryRepository.saveAndFlush(category);

        Category testCategory = categoryRepository.findById(category.getCategoryId()).orElse(null);

        assertThat(testCategory).isEqualTo(category);
        assertThat(testCategory.getCategoryId()).isEqualTo(category.getCategoryId());
        assertThat(testCategory.getCategoryName()).isEqualTo(category.getCategoryName());
        assertThat(testCategory.getCategoryEngName()).isEqualTo(category.getCategoryEngName());
        assertThat(testCategory.getIsActivated()).isTrue();
    }

}