
//package com.t2m.g2nee.shop.bookset.category.domain;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//import com.t2m.g2nee.shop.bookset.category.repository.CategoryRepository;
//import com.t2m.g2nee.shop.bookset.categoryPath.domain.CategoryPath;
//import com.t2m.g2nee.shop.bookset.categoryPath.repository.CategoryPathRepository;
//import com.t2m.g2nee.shop.config.ElasticsearchConfig;
//import com.t2m.g2nee.shop.config.MapperConfig;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.context.annotation.Import;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.context.TestPropertySource;
//
//@DataJpaTest
//@ActiveProfiles("test")
//@TestPropertySource(locations = "classpath:application-test.properties")
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//@Import(value = {MapperConfig.class, ElasticsearchConfig.class})
//class CategoryPathTest {
//
//    @Autowired
//    CategoryPathRepository categoryPathRepository;
//
//    @Autowired
//    CategoryRepository categoryRepository;
//    @Test
//    void test() {
//        Category aCategory = new Category("조상카테고리", "aCategory", true);
//        Category dCategory = new Category();
//        dCategory.setCategoryName("후손카테고리");
//        dCategory.setCategoryEngName("dCategory");
//
//        categoryRepository.saveAndFlush(aCategory);
//        categoryRepository.saveAndFlush(dCategory);
//
//        CategoryPath categoryPath = new CategoryPath(aCategory, dCategory, 1L);
//
//        categoryPathRepository.saveAndFlush(categoryPath);
//
//        CategoryPath testCategoryPath = categoryPathRepository.findById(categoryPath.getCategoryPathId()).orElse(null);
//
//        assertThat(testCategoryPath).isEqualTo(categoryPath);
//        assertThat(testCategoryPath.getCategoryPathId()).isEqualTo(categoryPath.getCategoryPathId());
//        assertThat(testCategoryPath.getAncestor()).isEqualTo(categoryPath.getAncestor());
//        assertThat(testCategoryPath.getDescendant()).isEqualTo(categoryPath.getDescendant());
//        assertThat(testCategoryPath.getDepth()).isEqualTo(categoryPath.getDepth());
//    }
//
//}