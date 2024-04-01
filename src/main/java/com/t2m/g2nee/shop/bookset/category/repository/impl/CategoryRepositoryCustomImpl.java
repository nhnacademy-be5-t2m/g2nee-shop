package com.t2m.g2nee.shop.bookset.category.repository.impl;

import com.querydsl.jpa.JPQLQuery;
import com.t2m.g2nee.shop.bookset.category.domain.Category;
import com.t2m.g2nee.shop.bookset.category.domain.QCategory;
import com.t2m.g2nee.shop.bookset.category.repository.CategoryRepositoryCustom;
import com.t2m.g2nee.shop.bookset.categoryPath.domain.QCategoryPath;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.data.support.PageableExecutionUtils;

public class CategoryRepositoryCustomImpl extends QuerydslRepositorySupport implements CategoryRepositoryCustom {
    public CategoryRepositoryCustomImpl() {
        super(Category.class);
    }

    @Override
    public Page<Category> getSubCategoriesByCategoryId(Long categoryId, Pageable pageable) {
        QCategory category = QCategory.category;
        QCategoryPath categoryPath = QCategoryPath.categoryPath;

        /**
         * SELECT c.*
         * FROM Categories c
         *          JOIN CategoryPaths p
         *               ON c.categoryId = p.descendantId
         * WHERE p.ancestorId = ?
         *   AND p.depth = 1
         * ORDER BY c.categoryName asc;
         */

        List<Category> subCategories = from(category)
                .innerJoin(categoryPath)
                .on(category.categoryId.eq(categoryPath.descendant.categoryId))
                .where(categoryPath.ancestor.categoryId.eq(categoryId)
                        .and(categoryPath.depth.eq(1L)))
                .select(category)
                .orderBy(category.categoryName.asc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize()).fetch();

        /**
         * SELECT count(categoryPathId)
         * FROM CategoryPaths p
         * WHERE p.ancestorId = ?
         *   AND p.depth = 1;
         */
        JPQLQuery<Long> count = from(categoryPath)
                .where(categoryPath.ancestor.categoryId.eq(categoryId)
                        .and(categoryPath.depth.eq(1L)))
                .select(categoryPath.categoryPathId.count());

        return PageableExecutionUtils.getPage(subCategories, pageable, count::fetchOne);
    }

    @Override
    public Page<Category> getRootCategories(Pageable pageable) {
        QCategory category = QCategory.category;
        QCategoryPath categoryPath = QCategoryPath.categoryPath;

        /**
         * SELECT c.*
         * FROM Categories c
         * LEFT JOIN CategoryPaths p ON c.categoryId = p.descendantId AND p.depth > 0
         * WHERE p.descendantId IS NULL;
         */

        List<Category> rootCategories = from(category)
                .leftJoin(categoryPath)
                .on(category.categoryId.eq(categoryPath.descendant.categoryId).and(categoryPath.depth.gt(0)))
                .where(categoryPath.descendant.categoryId.isNull())
                .select(category)
                .fetch();

        /**
         * SELECT count(categoryId)
         * FROM Categories c
         * LEFT JOIN CategoryPaths p ON c.categoryId = p.descendantId AND p.depth > 0
         * WHERE p.descendantId IS NULL;
         */
        JPQLQuery<Long> count = from(category)
                .leftJoin(categoryPath)
                .on(category.categoryId.eq(categoryPath.descendant.categoryId).and(categoryPath.depth.gt(0)))
                .where(categoryPath.descendant.categoryId.isNull())
                .select(category.categoryId.count());

        return PageableExecutionUtils.getPage(rootCategories, pageable, count::fetchOne);
    }

    @Override
    public List<Category> getFindAncestorIdsByCategoryId(Long categoryId) {
        QCategory category = QCategory.category;
        QCategoryPath ancestors = new QCategoryPath("ancestors");
        QCategoryPath descendant = new QCategoryPath("descendant");

        /**
         * SELECT c.*
         * FROM Categories c
         * JOIN CategoryPaths ancestors ON c.categoryId = ancestors.ancestorId
         * JOIN CategoryPaths descendants ON ancestors.descendantId = descendants.descendantId
         * WHERE descendant.descendantId = ?
         * GROUP BY c.categoryId
         * ORDER BY COUNT(ancestors.descendantId) DESC;
         */
        return from(category)
                .join(ancestors).on(category.categoryId.eq(ancestors.ancestor.categoryId))
                .join(descendant).on(ancestors.descendant.categoryId.eq(descendant.descendant.categoryId))
                .where(descendant.descendant.categoryId.eq(categoryId))
                .groupBy(category.categoryId)
                .orderBy()
                .select(category)
                .fetch();
    }
}
