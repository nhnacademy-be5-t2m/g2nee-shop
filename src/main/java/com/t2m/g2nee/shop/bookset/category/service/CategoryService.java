package com.t2m.g2nee.shop.bookset.category.service;


import com.t2m.g2nee.shop.bookset.category.dto.request.CategorySaveDto;
import com.t2m.g2nee.shop.bookset.category.dto.request.CategoryUpdateDto;
import com.t2m.g2nee.shop.bookset.category.dto.response.CategoryInfoDto;

/**
 * 비즈니스 로직에 맞춰 Category와 CategoryPath에 대해 저장, 수정, 삭제 하는 Service
 *
 * @author : 김수빈
 * @since : 1.0
 */
public interface CategoryService {

    /**
     * 카테고리 및 카테고리 경로 저장
     *
     * @param categorySaveDto
     * @return
     */
    CategoryInfoDto saveCategory(CategorySaveDto categorySaveDto);

    /**
     * 카테고리 및 카테고리 경로 수정
     *
     * @param updateCategoryDto
     * @return
     */
    CategoryInfoDto updateCategory(CategoryUpdateDto updateCategoryDto);

    /**
     * 카테고리 soft delete, 카테고리 경로는 delete
     *
     * @param categoryId
     * @return
     */
    boolean deleteCategory(Long categoryId);

    /**
     * 카테고리 활성화
     *
     * @param categoryId
     * @return
     */
    boolean activeCategory(Long categoryId);
}
