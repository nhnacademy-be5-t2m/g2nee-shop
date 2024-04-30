package com.t2m.g2nee.shop.bookset.category.repository.impl;

import com.querydsl.core.types.Projections;
import com.t2m.g2nee.shop.bookset.category.domain.Category;
import com.t2m.g2nee.shop.bookset.category.domain.QCategory;
import com.t2m.g2nee.shop.bookset.category.dto.response.CategoryUpdateDto;
import com.t2m.g2nee.shop.bookset.category.repository.CategoryRepositoryCustom;
import com.t2m.g2nee.shop.bookset.categorypath.domain.QCategoryPath;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

/**
 * QueryDSL을 사용하여 카테고리에 대한 복잡한 쿼리를 작성하기 위한 구현체입니다.
 *
 * @author : 김수빈
 * @since : 1.0
 */
public class CategoryRepositoryCustomImpl extends QuerydslRepositorySupport implements CategoryRepositoryCustom {

    /**
     * 영속성 컨텍스트를 관리하기 위한 EntityManager입니다.
     * update 쿼리 사용시, 영속성 업데이트를 위해 사용합니다.
     */
    private final EntityManager entityManager;

    /**
     * CategoryRepositoryCustomImpl의 생성자 입니다.
     *
     * @param entityManager 엔티티매니저
     */
    public CategoryRepositoryCustomImpl(EntityManager entityManager) {
        super(Category.class);
        this.entityManager = entityManager;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Category> getSubCategoriesByCategoryId(Long categoryId) {
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

        return from(category)
                .innerJoin(categoryPath)
                .on(category.categoryId.eq(categoryPath.descendant.categoryId))
                .where(categoryPath.ancestor.categoryId.eq(categoryId)
                        .and(categoryPath.depth.eq(1L))
                        .and(category.isActivated.isTrue()))
                .select(category)
                .orderBy(category.categoryName.asc()).fetch();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Category> getRootCategories() {
        QCategory category = QCategory.category;
        QCategoryPath categoryPath = QCategoryPath.categoryPath;


        /**
         * SELECT c.*
         * FROM Categories c
         * LEFT JOIN CategoryPaths p ON c.categoryId = p.descendantId AND p.depth > 0
         * WHERE p.descendantId IS NULL;
         */

        return from(category)
                .leftJoin(categoryPath)
                .on(category.categoryId.eq(categoryPath.descendant.categoryId).and(categoryPath.depth.gt(0)))
                .where(categoryPath.descendant.categoryId.isNull())
                .select(category)
                .fetch();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Category> getFindAncestorIdsByCategoryId(Long categoryId) {
        QCategory category = QCategory.category;
        QCategoryPath ancestors = new QCategoryPath("ancestors");
        QCategoryPath descendant = new QCategoryPath("descendant");

        /**
         * SELECT c.*
         * FROM Categories AS c
         * JOIN CategoryPaths AS ancestors ON c.categoryId = ancestors.descendantId
         * JOIN CategoryPaths AS descendant ON ancestors.descendantId = descendant.ancestorId
         * WHERE descendant.descendantId = 2
         * GROUP BY c.categoryId
         * ORDER BY COUNT(ancestors.descendantId) DESC;
         */
        return from(category)
                .join(ancestors).on(category.categoryId.eq(ancestors.descendant.categoryId))
                .join(descendant).on(ancestors.descendant.categoryId.eq(descendant.ancestor.categoryId))
                .where(descendant.descendant.categoryId.eq(categoryId))
                .groupBy(category.categoryId)
                .orderBy(ancestors.descendant.count().desc())
                .select(category)
                .fetch();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean getExistsByCategoryIdAndisActivated(Long categoryId, boolean active) {
        QCategory category = QCategory.category;

        /**
         *   SELECT CASE WHEN COUNT(categoryId) > 0 THEN true ELSE false END
         *   FROM Categories c
         *   WHERE c.categoryId = ?
         * 	AND c.isActivated is ?;
         */
        if (active) {
            return from(category)
                    .where(category.categoryId.eq(categoryId)
                            .and(category.isActivated.isTrue()))
                    .select(category.categoryId.count().gt(0)).fetchOne();
        } else {
            return from(category)
                    .where(category.categoryId.eq(categoryId)
                            .and(category.isActivated.isFalse()))
                    .select(category.categoryId.count().gt(0)).fetchOne();
        }

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void softDeleteByCategoryId(Long categoryId) {
        QCategory category = QCategory.category;

        /**
         * UPDATE Categories c SET c.isActivated = false WHERE c.categoryId = 1;
         */

        update(category)
                .set(category.isActivated, false)
                .where(category.categoryId.eq(categoryId)).execute();

        entityManager.clear();
        entityManager.flush();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void activeCategoryByCategoryId(Long categoryId) {
        QCategory category = QCategory.category;

        /**
         * UPDATE Categories c SET c.isActivated = true WHERE c.categoryId = 1;
         */
        update(category)
                .set(category.isActivated, true)
                .where(category.categoryId.eq(categoryId)).execute();

        entityManager.clear();
        entityManager.flush();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<CategoryUpdateDto> getFindByCategoryId(Long categoryId) {
        QCategory category = QCategory.category;
        QCategoryPath categoryPath = QCategoryPath.categoryPath;

        /**
         * SELECT c.*, COALESCE(p.ancestorId, 0)
         * FROM Categories c
         * 	LEFT JOIN CategoryPaths p ON c.categoryId = p.descendantId AND p.depth = 1
         * WHERE c.categoryId = ?;
         */

        return Optional.ofNullable(
                from(category)
                        .leftJoin(categoryPath).on(category.categoryId.eq(categoryPath.descendant.categoryId)
                                .and(categoryPath.depth.eq(1L)))
                        .where(category.categoryId.eq(categoryId))
                        .select(Projections.constructor(CategoryUpdateDto.class,
                                category.categoryId, category.categoryName, category.categoryEngName,
                                category.isActivated,
                                categoryPath.ancestor.categoryId.coalesce(0L)))
                        .fetchOne());
    }

}
