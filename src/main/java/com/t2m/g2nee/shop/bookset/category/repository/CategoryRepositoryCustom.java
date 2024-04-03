package com.t2m.g2nee.shop.bookset.category.repository;

import com.t2m.g2nee.shop.bookset.category.domain.Category;
import java.util.List;

/**
 * QueryDSL을 사용하여 복잡한 쿼리를 작성하기 위한 인터페이스
 */
public interface CategoryRepositoryCustom {

    /**
     * getSubCategoriesByCategoryId: categoryId에 해당하는 카테고리의 바로 하위 1단계 카테고리의 목록을 리턴
     */
    List<Category> getSubCategoriesByCategoryId(Long categoryId);

    /**
     * 최상위 카테고리만 반환
     *
     * @return
     */
    List<Category> getRootCategories();

    /**
     * categoryId를 포함하여 상위 카테고리를 반환
     * insert시 사용
     */
    List<Category> getFindAncestorIdsByCategoryId(Long categoryId);

    /**
     * 해당 카테고리가 존재하면서 active인지 확인
     */
    boolean getExistsByCategoryIdAndIsActive(Long categoryId, boolean active);

    /**
     * 카테고리를 비활성화로 변경
     */
    void softDeleteByCategoryId(Long categoryId);

    /**
     * 카테고리를 활성화로 변경
     */
    void activeCategoryByCategoryId(Long categoryId);
}
