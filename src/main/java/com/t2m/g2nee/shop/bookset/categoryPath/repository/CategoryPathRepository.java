package com.t2m.g2nee.shop.bookset.categoryPath.repository;

import com.t2m.g2nee.shop.bookset.category.domain.Category;
import com.t2m.g2nee.shop.bookset.categoryPath.domain.CategoryPath;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * CategoryPath Repository
 * JpaRepository와 CategoryPathRepositoryCustom 상속받아 메소드를 사용하게 함
 */
public interface CategoryPathRepository extends JpaRepository<CategoryPath, Long> {

    /**
     * existsByAncestorAndDescendant: 해당하는 조상, 자식을 갖는 경로가 있는지 확인
     */
    boolean existsByAncestorAndDescendant(Category ancestor, Category descendant);

    /**
     * existsByDescendant_CategoryId: CategoryPath에 descendantId에 해당하는 경로가 있는지 확인
     */
    boolean existsByDescendant_CategoryId(Long descendantId);

    void deleteByAncestor_CategoryId(Long ancestorId);

    void deleteByDescendant_CategoryId(Long descendantId);
}
