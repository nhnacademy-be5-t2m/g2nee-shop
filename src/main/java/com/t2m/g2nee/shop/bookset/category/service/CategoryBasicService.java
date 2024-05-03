package com.t2m.g2nee.shop.bookset.category.service;

import com.t2m.g2nee.shop.bookset.category.domain.Category;
import java.util.List;

/**
 * 카테고리의 기본적인 생성, 수정, 삭제를 위한 서비스 입니다.
 *
 * @author : 김수빈
 * @since : 1.0
 */
public interface CategoryBasicService {


    /**
     * 카테고리를 저장하는 메소드입니다.
     *
     * @param category 저장할 카테고리 객체
     * @return Category
     */
    Category saveCategoryBasic(Category category);

    /**
     * 카테고리를 수정하는 메소드 입니다.
     *
     * @param category 수정할 카테고리 객체
     * @return Category
     */
    Category updateCategoryBasic(Category category);

    /**
     * 카테고리를 soft delete하는 메소드입니다.
     *
     * @param categoryId 카테고리 id
     * @return boolen 성공시 false
     */
    boolean deleteCategoryBasic(Long categoryId);

    /**
     * 특정 카테고리의 조상 목록 반환합니다. 저장 시 사용합니다.
     *
     * @param descendantId 카테고리 id
     * @return List<Category>
     */
    List<Category> getAncestorList(Long descendantId);

    /**
     * 카테고리 활성화하는 메소드입니다.
     *
     * @param categoryId 카테고리 id
     * @return Category
     */
    Category activeCategory(Long categoryId);
}