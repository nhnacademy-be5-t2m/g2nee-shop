package com.t2m.g2nee.shop.bookset.category.repository;

import com.t2m.g2nee.shop.bookset.category.domain.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Category Repository
 * JpaRepository와 CategoryRepositoryCustom을 상속받아 메소드를 사용하게 함
 */
public interface CategoryRepository extends JpaRepository<Category, Long>, CategoryRepositoryCustom {

    /**
     * existsByCategoryName: name을 받아 name과 동일한 CategoryName이 있는지 확인
     */
    boolean existsByCategoryName(String name);

    Page<Category> findAll(Pageable pageable);

    Page<Category> findByCategoryNameContaining(String name, Pageable pageable);
}
