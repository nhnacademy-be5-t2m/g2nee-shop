package com.t2m.g2nee.shop.bookset.category.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.t2m.g2nee.shop.bookset.category.dto.response.CategoryHierarchyDto;
import com.t2m.g2nee.shop.bookset.category.dto.response.CategoryInfoDto;
import com.t2m.g2nee.shop.bookset.category.dto.response.CategoryUpdateDto;
import com.t2m.g2nee.shop.bookset.category.service.CategoryQueryService;
import com.t2m.g2nee.shop.pageUtils.PageResponse;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class CategoryQueryRestControllerTest {

    CategoryInfoDto category1;
    CategoryInfoDto category2;
    CategoryInfoDto category3;
    CategoryInfoDto category4;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private CategoryQueryService service;

    @BeforeEach
    void setUp() {
        category1 = new CategoryInfoDto(1L, "카테고리1", "test1", true);
        category2 = new CategoryInfoDto(2L, "카테고리2", "test2", true);
        category3 = new CategoryInfoDto(3L, "카테고리3", "test3", true);
        category4 = new CategoryInfoDto(4L, "카테고리4", "test4", true);

        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetRootCategories() throws Exception {
        CategoryHierarchyDto c1 = new CategoryHierarchyDto(category1.getCategoryId(), category1.getCategoryName(),
                category1.getCategoryEngName(), category1.getIsActivated());
        CategoryHierarchyDto c2 = new CategoryHierarchyDto(category2.getCategoryId(), category2.getCategoryName(),
                category2.getCategoryEngName(), category2.getIsActivated());
        CategoryHierarchyDto c3 = new CategoryHierarchyDto(category3.getCategoryId(), category3.getCategoryName(),
                category3.getCategoryEngName(), category3.getIsActivated());
        CategoryHierarchyDto c4 = new CategoryHierarchyDto(category4.getCategoryId(), category4.getCategoryName(),
                category4.getCategoryEngName(), category4.getIsActivated());

        List<CategoryHierarchyDto> rootCategory = List.of(c1, c2);
        c1.setChildren(List.of(c3));
        c3.setChildren(List.of(c4));

        when(service.getRootCategories()).thenReturn(rootCategory);

        mockMvc.perform(get("/shop/categories"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$.[0].categoryId", equalTo(1)))
                .andExpect(jsonPath("$.[0].categoryName", equalTo("카테고리1")))
                .andExpect(jsonPath("$.[0].categoryEngName", equalTo("test1")))
                .andExpect(jsonPath("$.[0].children.[0].categoryId", equalTo(3)))
                .andExpect(jsonPath("$.[0].children.[0].children.[0].categoryId", equalTo(4)));
    }


    @Test
    void testGetCategory() throws Exception {
        CategoryUpdateDto c1 = new CategoryUpdateDto(category1.getCategoryId(), category1.getCategoryName(),
                category1.getCategoryEngName(), category1.getIsActivated(), 0L);
        CategoryUpdateDto c3 = new CategoryUpdateDto(category3.getCategoryId(), category3.getCategoryName(),
                category3.getCategoryEngName(), category3.getIsActivated(), 1L);
        c1.setChildren(List.of(c3));

        when(service.getCategory(anyLong())).thenReturn(c1);

        mockMvc.perform(get("/shop/categories/{categoryId}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.categoryId", equalTo(1)))
                .andExpect(jsonPath("$.categoryName", equalTo("카테고리1")))
                .andExpect(jsonPath("$.categoryEngName", equalTo("test1")));
    }

    @Test
    void testGetAllCategories() throws Exception {
        List<CategoryInfoDto> categories = List.of(category1, category2, category3, category4);

        when(service.getAllCategories()).thenReturn(categories);

        mockMvc.perform(get("/shop/categories/all"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(4)))
                .andExpect(jsonPath("$.[0].categoryId", equalTo(1)))
                .andExpect(jsonPath("$.[1].categoryId", equalTo(2)))
                .andExpect(jsonPath("$.[2].categoryId", equalTo(3)))
                .andExpect(jsonPath("$.[3].categoryId", equalTo(4)));
    }

    @Test
    void testGetCategories() throws Exception {
        List<CategoryInfoDto> categories = List.of(category1, category2, category3, category4);

        PageResponse<CategoryInfoDto> categoryPage =
                PageResponse.<CategoryInfoDto>builder()
                        .data(categories)
                        .currentPage(1)
                        .totalPage(1)
                        .build();

        when(service.getCategoriesByName(anyString(), anyInt())).thenReturn(categoryPage);

        mockMvc.perform(get("/shop/categories/search")
                        .param("name", "카테고리")
                        .param("page", "1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data", hasSize(4)))
                .andExpect(jsonPath("$.data[0].categoryId", equalTo(1)))
                .andExpect(jsonPath("$.data[1].categoryId", equalTo(2)))
                .andExpect(jsonPath("$.data[2].categoryId", equalTo(3)))
                .andExpect(jsonPath("$.data[3].categoryId", equalTo(4)));

    }

}
