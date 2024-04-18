package com.t2m.g2nee.shop.bookset.category.service;


import com.t2m.g2nee.shop.bookset.category.dto.request.CategorySaveDto;
import com.t2m.g2nee.shop.bookset.category.dto.response.CategoryInfoDto;

/**
 * 비즈니스 로직에 맞춰 Category와 CategoryPath에 대해 저장, 수정, 삭제하는 서비스 입니다.
 * @author : 김수빈
 * @since : 1.0
 */
public interface CategoryService {

    /**
     * 카테고리 및 카테고리 경로 저장합니다.
     *
     * @param categorySaveDto 카테고리 저장 객체
     * @return CategoryInfoDto
     */
    CategoryInfoDto saveCategory(CategorySaveDto categorySaveDto);

    /**
     * 카테고리 및 카테고리 경로를 수정합니다.
     * @param categoryId 카테고리 id
     * @param categorySaveDto 카테고리 저장 객체
     * @return CategoryInfoDto
     */
    CategoryInfoDto updateCategory(Long categoryId, CategorySaveDto categorySaveDto);

    /**
     * 카테고리 soft delete하고, 관련된 카테고리 경로는 delete합니다.
     * @param categoryId 카테고리 id
     * @return boolean 성공 시 false 반환 
     */
    boolean deleteCategory(Long categoryId);

    /**
     * 카테고리 활성화하고, 최상위 카테고리로 경로를 저장합니다.
     * @param categoryId 카테고리 id
     * @return boolean 성공 시 ture 반환
     */
    boolean activeCategory(Long categoryId);
}
