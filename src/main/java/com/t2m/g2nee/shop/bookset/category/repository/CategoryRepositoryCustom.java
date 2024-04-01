package com.t2m.g2nee.shop.bookset.category.repository;

import com.t2m.g2nee.shop.bookset.category.domain.Category;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * QueryDSL을 사용하여 복잡한 쿼리를 작성하기 위한 인터페이스
 */
public interface CategoryRepositoryCustom {

    /**
     * getSubCategoriesByCategoryId: categoryId에 해당하는 카테고리의 바로 하위 1단계 카테고리의 목록을 리턴
     */
    Page<Category> getSubCategoriesByCategoryId(Long categoryId, Pageable pageable);

    /**
     * 최상위 카테고리만 반환
     *
     * @param pageable
     * @return
     */
    Page<Category> getRootCategories(Pageable pageable);

    /**
     * categoryId를 포함하여 상위 카테고리를 반환
     * insert시 사용
     */
    List<Category> getFindAncestorIdsByCategoryId(Long categoryId);
}
