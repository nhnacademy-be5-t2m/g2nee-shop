package com.t2m.g2nee.shop.bookset.category.service;


import com.t2m.g2nee.shop.bookset.category.dto.request.CategorySaveDto;
import com.t2m.g2nee.shop.bookset.category.dto.request.CategoryUpdateDto;
import com.t2m.g2nee.shop.bookset.category.dto.response.CategoryInfoDto;

/**
 * 비즈니스 로직에 맞춰 Category와 CategoryPath에 대해 저장, 수정, 삭제 하는 Service
 */
public interface CategoryService {

    //카테고리 및 관련 카테고리 경로 저장
    CategoryInfoDto saveCategory(CategorySaveDto categorySaveDto);

    //카테고리 및 관련 카테고리 경로 수정
    CategoryInfoDto updateCategory(CategoryUpdateDto updateCategoryDto);

    //카테고리 및 관련 카테고리 경로 삭제
    boolean deleteCategory(Long categoryId);

    boolean activeCategory(Long categoryId);
}
