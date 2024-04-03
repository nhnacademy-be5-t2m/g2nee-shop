package com.t2m.g2nee.shop.bookset.category.controller;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.t2m.g2nee.shop.bookset.category.dto.request.CategorySaveDto;
import com.t2m.g2nee.shop.bookset.category.dto.request.CategoryUpdateDto;
import com.t2m.g2nee.shop.bookset.category.dto.response.CategoryInfoDto;
import com.t2m.g2nee.shop.bookset.category.service.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class CategoryControllerTest {

    ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private CategoryService service;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();

    }

    @Test
    void testCreateCategory() throws Exception {
        CategorySaveDto request = new CategorySaveDto("테스트", "test", 0L);
        CategoryInfoDto category = new CategoryInfoDto(1L, "테스트", "test");

        when(service.saveCategory(any(CategorySaveDto.class))).thenReturn(category);

        mockMvc.perform(post("/shop/categories")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.categoryId", equalTo(1)))
                .andExpect(jsonPath("$.categoryName", equalTo(category.getCategoryName())))
                .andExpect(jsonPath("$.categoryEngName", equalTo(category.getCategoryEngName())));
    }

    @Test
    void testUpdateCategory() throws Exception {
        CategorySaveDto request = new CategorySaveDto("수정", "modify", 0L);
        CategoryInfoDto updatedCategory = new CategoryInfoDto(1L, "수정", "modify");

        when(service.updateCategory(any(CategoryUpdateDto.class))).thenReturn(updatedCategory);

        mockMvc.perform(put("/shop/categories/{categoryId}/update", 1L)
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.categoryId", equalTo(1)))
                .andExpect(jsonPath("$.categoryName", equalTo(updatedCategory.getCategoryName())))
                .andExpect(jsonPath("$.categoryEngName", equalTo(updatedCategory.getCategoryEngName())));

    }

    @Test
    void testDeleteCategory() throws Exception {
        when(service.deleteCategory(anyLong())).thenReturn(false);

        mockMvc.perform(delete("/shop/categories/{categoryId}/delete", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data", equalTo(false)));

    }

    @Test
    void testActiveCategory() throws Exception {
        when(service.activeCategory(anyLong())).thenReturn(true);

        mockMvc.perform(put("/shop/categories/{categoryId}/active", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data", equalTo(true)));
    }
}
