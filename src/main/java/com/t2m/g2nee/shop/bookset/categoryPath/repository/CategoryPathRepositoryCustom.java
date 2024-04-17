package com.t2m.g2nee.shop.bookset.categoryPath.repository;

public interface CategoryPathRepositoryCustom {

    /**
     * categoryId가 자손과 조상에 해당하는 경로 모두 삭제
     *
     * @param categoryId
     */
    void deleteCategoryPathByAncestorIdAndDescendantId(Long categoryId);
}