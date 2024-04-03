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

import com.t2m.g2nee.shop.bookset.category.dto.response.CategoryInfoDto;
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
        category1 = new CategoryInfoDto(1L, "카테고리1", "test1");
        category2 = new CategoryInfoDto(2L, "카테고리2", "test2");
        category3 = new CategoryInfoDto(3L, "카테고리3", "test3");
        category4 = new CategoryInfoDto(4L, "카테고리4", "test4");

        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetRootCategories() throws Exception {
        List<CategoryInfoDto> rootCategory = List.of(category1, category2);

        when(service.getRootCategories()).thenReturn(rootCategory);

        mockMvc.perform(get("/shop/categories"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data", hasSize(2)))
                .andExpect(jsonPath("$.data[0].categoryId", equalTo(1)))
                .andExpect(jsonPath("$.data[0].categoryName", equalTo("카테고리1")))
                .andExpect(jsonPath("$.data[0].categoryEngName", equalTo("test1")))
                .andExpect(jsonPath("$.data[1].categoryId", equalTo(2)))
                .andExpect(jsonPath("$.data[1].categoryName", equalTo("카테고리2")))
                .andExpect(jsonPath("$.data[1].categoryEngName", equalTo("test2")));
    }

    @Test
    void testGetSubCategories() throws Exception {

        List<CategoryInfoDto> subCategory = List.of(category3, category4);

        PageResponse<CategoryInfoDto> categoryPage =
                PageResponse.<CategoryInfoDto>builder()
                        .data(subCategory)
                        .currentPage(1)
                        .totalPage(1)
                        .build();

        when(service.getSubCategories(anyLong(), anyInt())).thenReturn(categoryPage);

        mockMvc.perform(get("/shop/categories/{categoryId}/sub", 1L).param("page", "1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data", hasSize(2)))
                .andExpect(jsonPath("$.data[0].categoryId", equalTo(3)))
                .andExpect(jsonPath("$.data[0].categoryName", equalTo("카테고리3")))
                .andExpect(jsonPath("$.data[0].categoryEngName", equalTo("test3")))
                .andExpect(jsonPath("$.data[1].categoryId", equalTo(4)))
                .andExpect(jsonPath("$.data[1].categoryName", equalTo("카테고리4")))
                .andExpect(jsonPath("$.data[1].categoryEngName", equalTo("test4")));
    }

    @Test
    void testGetCategory() throws Exception {

        when(service.getCategory(anyLong())).thenReturn(category1);

        mockMvc.perform(get("/shop/categories/{categoryId}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data.categoryId", equalTo(1)))
                .andExpect(jsonPath("$.data.categoryName", equalTo("카테고리1")))
                .andExpect(jsonPath("$.data.categoryEngName", equalTo("test1")));
    }

    @Test
    void testGetAllCategories() throws Exception {
        List<CategoryInfoDto> categories = List.of(category1, category2, category3, category4);

        PageResponse<CategoryInfoDto> categoryPage =
                PageResponse.<CategoryInfoDto>builder()
                        .data(categories)
                        .currentPage(1)
                        .totalPage(1)
                        .build();

        when(service.getAllCategories(anyInt())).thenReturn(categoryPage);

        mockMvc.perform(get("/shop/categories/all").param("page", "1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data", hasSize(4)))
                .andExpect(jsonPath("$.data[0].categoryId", equalTo(1)))
                .andExpect(jsonPath("$.data[1].categoryId", equalTo(2)))
                .andExpect(jsonPath("$.data[2].categoryId", equalTo(3)))
                .andExpect(jsonPath("$.data[3].categoryId", equalTo(4)));
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

        mockMvc.perform(get("/shop/categories/search/{categoryName}", "카테고리").param("page", "1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data", hasSize(4)))
                .andExpect(jsonPath("$.data[0].categoryId", equalTo(1)))
                .andExpect(jsonPath("$.data[1].categoryId", equalTo(2)))
                .andExpect(jsonPath("$.data[2].categoryId", equalTo(3)))
                .andExpect(jsonPath("$.data[3].categoryId", equalTo(4)));

    }

}
