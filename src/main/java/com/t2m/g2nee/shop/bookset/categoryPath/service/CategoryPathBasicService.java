package com.t2m.g2nee.shop.bookset.categoryPath.service;


import com.t2m.g2nee.shop.bookset.categoryPath.domain.CategoryPath;

/**
 * CategoryPaths 테이블의 기본 저장, 삭제 Service
 *
 * @author : 김수빈
 * @since : 1.0
 */
public interface CategoryPathBasicService {

    CategoryPath saveCategoryPath(CategoryPath categoryPath);

    void deleteCategoryPathBasic(Long categoryId);
}
