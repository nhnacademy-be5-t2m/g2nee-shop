package com.t2m.g2nee.shop.bookset.categoryPath.repository;

import com.t2m.g2nee.shop.bookset.category.domain.Category;
import com.t2m.g2nee.shop.bookset.categoryPath.domain.CategoryPath;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * CategoryPath Repository
 * JpaRepository와 CategoryPathRepositoryCustom 상속받아 메소드를 사용하게 함
 *
 * @author : 김수빈
 * @since : 1.0
 */
public interface CategoryPathRepository extends JpaRepository<CategoryPath, Long>, CategoryPathRepositoryCustom {

    /**
     * path의 (조상, 자식)경로가 있는지 확인
     *
     * @param ancestor
     * @param descendant
     * @return
     */
    boolean existsByAncestorAndDescendant(Category ancestor, Category descendant);
}
