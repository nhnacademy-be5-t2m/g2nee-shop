package com.t2m.g2nee.shop.bookset.category.repository;

import com.t2m.g2nee.shop.bookset.category.domain.Category;
import com.t2m.g2nee.shop.bookset.category.dto.response.CategoryUpdateDto;
import java.util.List;
import java.util.Optional;

/**
 * QueryDSL을 사용하여 복잡한 쿼리를 통해 카테고리 데이터베이스에 접근하기 위한 인터페이스 입니다.
 *
 * @author : 김수빈
 * @since : 1.0
 */
public interface CategoryRepositoryCustom {

    /**
     * 특정 카테고리의 바로 하위 카테고리들을 반환하는 메소드입니다.
     *
     * @param categoryId 특정 카테고리 id
     * @return List<Category>
     */
    List<Category> getSubCategoriesByCategoryId(Long categoryId);

    /**
     * 최상위 카테고리만 반환하는 메소드입니다.
     *
     * @return List<Category>
     */
    List<Category> getRootCategories();

    /**
     * 특정 카테고리를 포함하여 조상들을 반환하는 메소드입니다.
     * depth가 작은 순서로 List에 저장됩니다.
     *
     * @param categoryId 카테고리 id
     * @return List<Category>
     */
    List<Category> getFindAncestorIdsByCategoryId(Long categoryId);

    /**
     * 특정 카테고리가 존재하면서 활성/비활성되어 있는지 확인하는 메소드입니다.
     *
     * @param categoryId 카테고리 id
     * @param active     true일 경우 활성인지 확인, false의 경우 비활성인지 확인
     * @return 존재하면 true, 없으면 false
     */
    boolean getExistsByCategoryIdAndisActivated(Long categoryId, boolean active);

    /**
     * 카테고리를 soft delete하여 비활성화하는 메소드입니다.
     *
     * @param categoryId 카테고리 id
     */
    void softDeleteByCategoryId(Long categoryId);

    /**
     * 카테고리를 활성화하는 메소드 입니다.
     *
     * @param categoryId 카테고리 id
     */
    void activeCategoryByCategoryId(Long categoryId);

    /**
     * 특정 카테고리를 찾는데, 바로 상위 카테고리의 id를 함께 찾는 메소드 입니다.
     * 최상위 카테고리의 경우 바로 상위 카테고리 id 값은 0입니다.
     *
     * @param categoryId 카테고리 id
     * @return CategoryUpdateDto
     */
    Optional<CategoryUpdateDto> getFindByCategoryId(Long categoryId);
}
