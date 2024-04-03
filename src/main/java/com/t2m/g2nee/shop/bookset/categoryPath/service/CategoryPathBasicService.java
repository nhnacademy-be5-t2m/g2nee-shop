package com.t2m.g2nee.shop.bookset.categoryPath.service;


import com.t2m.g2nee.shop.bookset.categoryPath.domain.CategoryPath;

/**
 * CategoryPaths 테이블의 기본 저장, 수정, 삭제 Service
 */
public interface CategoryPathBasicService {

    CategoryPath saveCategoryPath(CategoryPath categoryPath);

    void deleteCategoryPathBasic(Long categoryId);
}
