package com.t2m.g2nee.shop.bookset.categorypath.repository.impl;

import com.t2m.g2nee.shop.bookset.categorypath.domain.CategoryPath;
import com.t2m.g2nee.shop.bookset.categorypath.domain.QCategoryPath;
import com.t2m.g2nee.shop.bookset.categorypath.repository.CategoryPathRepositoryCustom;
import javax.persistence.EntityManager;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

/**
 * QueryDSL을 사용하여 카테고리 경로에 대한 복잡한 쿼리를 작성하기 위한 구현체입니다.
 *
 * @author : 김수빈
 * @since : 1.0
 */
public class CategoryPathRepositoryCustomImpl extends QuerydslRepositorySupport implements
        CategoryPathRepositoryCustom {

    /**
     * 영속성 컨텍스트를 관리하기 위한 EntityManager입니다.
     * update 쿼리 사용시, 영속성 업데이트를 위해 사용합니다.
     */
    private final EntityManager entityManager;
    QCategoryPath categoryPath = QCategoryPath.categoryPath;

    /**
     * CategoryPathRepositoryCustomImpl
     *
     * @param entityManager 엔티티 매니저
     */
    public CategoryPathRepositoryCustomImpl(EntityManager entityManager) {
        super(CategoryPath.class);
        this.entityManager = entityManager;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteCategoryPathByAncestorIdAndDescendantId(Long categoryId) {

        /**
         * DELETE FROM t2m_dev.CategoryPaths
         * WHERE ancestorId = ? OR descendantId = ?;
         */

        delete(categoryPath)
                .where(categoryPath.descendant.categoryId.eq(categoryId)
                        .or(categoryPath.ancestor.categoryId.eq(categoryId)))
                .execute();

        entityManager.clear();
        entityManager.flush();

    }
}
