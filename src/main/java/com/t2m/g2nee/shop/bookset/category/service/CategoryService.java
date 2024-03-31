package com.t2m.g2nee.shop.bookset.category.service;


import com.t2m.g2nee.shop.bookset.category.domain.Category;
import com.t2m.g2nee.shop.bookset.category.dto.request.CategorySaveDto;
import com.t2m.g2nee.shop.bookset.category.dto.request.CategoryUpdateDto;

/**
 * 비즈니스 로직에 맞춰 Category와 CategoryPath에 대해 저장, 수정, 삭제 하는 Service
 */
public interface CategoryService {

    //카테고리 및 관련 카테고리 경로 저장
    Category saveCategory(CategorySaveDto categorySaveDto);

    //카테고리 및 관련 카테고리 경로 수정
    Category updateCategory(CategoryUpdateDto updateCategoryDto);

    //카테고리 및 관련 카테고리 경로 삭제
    void deleteCategory(Long categoryId);
}
