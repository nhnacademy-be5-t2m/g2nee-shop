package com.t2m.g2nee.shop.bookset.categoryPath.repository;

/**
 * QueryDSL을 사용하여 복잡한 쿼리를 통해 카테고리 경로 데이터베이스에 접근하기 위한 인터페이스 입니다.
 *
 * @author : 김수빈
 * @since : 1.0
 */
public interface CategoryPathRepositoryCustom {

    /**
     * categoryId가 자손과 조상에 해당하는 경로 모두 삭제합니다.
     *
     * @param categoryId
     */
    void deleteCategoryPathByAncestorIdAndDescendantId(Long categoryId);
}