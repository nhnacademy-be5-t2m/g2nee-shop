package com.t2m.g2nee.shop.bookset.categoryPath.repository;

import com.t2m.g2nee.shop.bookset.category.domain.Category;
import com.t2m.g2nee.shop.bookset.categoryPath.domain.CategoryPath;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 카테고리 경로 엔티티의 데이터베이스 테이블에 접근하는 메소드를 사용하기 위한 인터페이스입니다.
 *
 * @author : 김수빈
 * @since : 1.0
 */
public interface CategoryPathRepository extends JpaRepository<CategoryPath, Long>, CategoryPathRepositoryCustom {

    /**
     * path의 (조상, 자식)경로가 있는지 확인합니다.
     *
     * @param ancestor
     * @param descendant
     * @return
     */
    boolean existsByAncestorAndDescendant(Category ancestor, Category descendant);
}
