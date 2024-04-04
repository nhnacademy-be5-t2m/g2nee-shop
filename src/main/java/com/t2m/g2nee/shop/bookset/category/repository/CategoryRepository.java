package com.t2m.g2nee.shop.bookset.category.repository;

import com.t2m.g2nee.shop.bookset.category.domain.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 카테고리 레포지토리
 *
 * @author : 김수빈
 * @since : 1.0
 * JpaRepository와 CategoryRepositoryCustom을 상속받아 메소드를 사용하게 함
 */
public interface CategoryRepository extends JpaRepository<Category, Long>, CategoryRepositoryCustom {

    /**
     * name을 포함하는 카테고리가 존재하는지 확인하는 메소드
     * @param name
     * @return
     */
    boolean existsByCategoryName(String name);

    /**
     * 모든 카테고리를 찾아 페이징 처리하는 메소드
     *
     * @param pageable
     * @return
     */
    Page<Category> findAll(Pageable pageable);

    /**
     * 카테고리를 name으로 찾아 결과를 페이징 처리하는 메소드
     * @param name
     * @param pageable
     * @return
     */
    Page<Category> findByCategoryNameContaining(String name, Pageable pageable);
}
