package com.t2m.g2nee.shop.bookset.category.service;

import com.t2m.g2nee.shop.bookset.category.domain.Category;
import java.util.List;

/**
 * Categories 테이블의 기본적인 생성, 수정, 삭제를 위한 BasicService
 *
 * @author : 김수빈
 * @since : 1.0
 */
public interface CategoryBasicService {


    /**
     * 카테고리 저장
     *
     * @param category
     * @return
     */
    Category saveCategoryBasic(Category category);

    /**
     * 카테고리 수정
     *
     * @param category
     * @return
     */
    Category updateCategoryBasic(Category category);

    /**
     * 카테고리 soft delete
     *
     * @param categoryId
     * @return
     */
    boolean deleteCategoryBasic(Long categoryId);

    /**
     * 특정 카테고리의 조상 목록 반환
     * insert 시 사용
     * 가장 depth가 작은 조상부터 저장되어 있음
     *
     * @param descendantId
     * @return
     */
    List<Category> getAncestorList(Long descendantId);

    /**
     * 카테고리 활성화
     *
     * @param categoryId
     * @return
     */
    Category activeCategory(Long categoryId);
}