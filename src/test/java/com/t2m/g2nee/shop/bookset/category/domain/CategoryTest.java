//package com.t2m.g2nee.shop.bookset.category.domain;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//import com.t2m.g2nee.shop.bookset.book.mapper.BookMapper;
//import com.t2m.g2nee.shop.bookset.category.repository.CategoryRepository;
//import com.t2m.g2nee.shop.config.MapperConfig;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.context.annotation.Import;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.TestPropertySource;
//
//@DataJpaTest
//@ActiveProfiles("test")
//@TestPropertySource(locations = "classpath:application-test.properties")
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//@Import(value = {MapperConfig.class})
//class CategoryTest {
//
//    @Autowired
//    CategoryRepository categoryRepository;
//
//    @Test
//    void test() {
//        Category category = new Category("테스트카테고리", "testCategory");
//
//        categoryRepository.saveAndFlush(category);
//
//        Category testCategory = categoryRepository.findById(category.getCategoryId()).orElse(null);
//
//        assertThat(testCategory).isEqualTo(category);
//        assertThat(testCategory.getCategoryId()).isEqualTo(category.getCategoryId());
//        assertThat(testCategory.getCategoryName()).isEqualTo(category.getCategoryName());
//        assertThat(testCategory.getCategoryEngName()).isEqualTo(category.getCategoryEngName());
//        assertThat(testCategory.isActivated()).isTrue();
//    }
//
//}