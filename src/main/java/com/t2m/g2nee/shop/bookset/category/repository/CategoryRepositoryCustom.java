package com.t2m.g2nee.shop.bookset.category.repository;

import com.t2m.g2nee.shop.bookset.category.domain.Category;
import com.t2m.g2nee.shop.bookset.category.dto.response.CategoryUpdateDto;
import java.util.List;

/**
 * QueryDSL을 사용하여 복잡한 쿼리를 작성하기 위한 인터페이스
 *
 * @author : 김수빈
 * @since : 1.0
 */
public interface CategoryRepositoryCustom {

    /**
     * 특정 카테고리의 바로 하위 카테고리를 반환하는 메소드
     *
     * @param categoryId
     * @return
     */
    List<Category> getSubCategoriesByCategoryId(Long categoryId);

    /**
     * 최상위 카테고리만 반환하는 메소드
     *
     * @return
     */
    List<Category> getRootCategories();

    /**
     * 특정 카테고리의 조상을 찾는 메소드
     * 특정 카테고리와 가장 depth가 작은 조상부터 출력
     *
     * @param categoryId
     * @return
     */
    List<Category> getFindAncestorIdsByCategoryId(Long categoryId);

    /**
     * 해당 카테고리가 존재하면서 활성/비황성되어 있는지 확인
     *
     * @param categoryId
     * @param active
     * @return
     */
    boolean getExistsByCategoryIdAndisActivated(Long categoryId, boolean active);

    /**
     * 카테고리를 비활성화
     *
     * @param categoryId
     */
    void softDeleteByCategoryId(Long categoryId);

    /**
     * 카테고리를 활성화
     *
     * @param categoryId
     */
    void activeCategoryByCategoryId(Long categoryId);

    CategoryUpdateDto getFindByCategoryId(Long categoryId);
}
