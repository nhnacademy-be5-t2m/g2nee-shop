package com.t2m.g2nee.shop.bookset.categorypath.service;


import com.t2m.g2nee.shop.bookset.categorypath.domain.CategoryPath;

/**
 * CategoryPaths 테이블의 기본 저장, 삭제 Service
 *
 * @author : 김수빈
 * @since : 1.0
 */
public interface CategoryPathBasicService {

    /**
     * 카테고리 경로를 저장합니다.
     *
     * @param categoryPath 저장할 카테고리 경로 객체
     * @return 저장된 CategoryPath
     */
    CategoryPath saveCategoryPath(CategoryPath categoryPath);

    /**
     * 카테고리 경로를 삭제합니다.
     *
     * @param categoryId 삭제할 카테고리 id
     */
    void deleteCategoryPathBasic(Long categoryId);
}
