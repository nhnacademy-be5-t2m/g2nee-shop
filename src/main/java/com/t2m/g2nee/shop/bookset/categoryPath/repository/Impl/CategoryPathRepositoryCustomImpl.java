package com.t2m.g2nee.shop.bookset.categoryPath.repository.Impl;

import com.t2m.g2nee.shop.bookset.categoryPath.domain.CategoryPath;
import com.t2m.g2nee.shop.bookset.categoryPath.domain.QCategoryPath;
import com.t2m.g2nee.shop.bookset.categoryPath.repository.CategoryPathRepositoryCustom;
import javax.persistence.EntityManager;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

public class CategoryPathRepositoryCustomImpl extends QuerydslRepositorySupport implements
        CategoryPathRepositoryCustom {

    private final EntityManager entityManager;

    public CategoryPathRepositoryCustomImpl(EntityManager entityManager) {
        super(CategoryPath.class);
        this.entityManager = entityManager;
    }

    @Override
    public void deleteCategoryPathByAncestorIdAndDescendantId(Long categoryId) {
        QCategoryPath categoryPath = QCategoryPath.categoryPath;

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