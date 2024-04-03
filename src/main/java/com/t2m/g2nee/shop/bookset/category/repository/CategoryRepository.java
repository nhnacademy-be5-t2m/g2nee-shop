package com.t2m.g2nee.shop.bookset.category.repository;

import com.t2m.g2nee.shop.bookset.category.domain.Category;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

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

    Optional<Category> findByCategoryName(String categoryName);

    @Modifying
    @Query("DELETE FROM BookCategory bc WHERE bc.book.bookId = :bookId")
    void deleteByBookId(Long bookId);
}
