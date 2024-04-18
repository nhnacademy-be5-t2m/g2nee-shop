package com.t2m.g2nee.shop.bookset.category.repository;

import com.t2m.g2nee.shop.bookset.category.domain.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


/**
 * 카테고리 엔티티의 데이터베이스 테이블에 접근하는 메소드를 사용하기 위한 인터페이스입니다.
 * @author : 김수빈
 * @since : 1.0
 */
public interface CategoryRepository extends JpaRepository<Category, Long>, CategoryRepositoryCustom {

    /**
     * name을 포함하는 카테고리가 존재하는지 확인하는 메소드입니다.
     * @param name 카테고리 이름
     * @return boolean
     */
    boolean existsByCategoryName(String name);

    /**
     * 카테고리를 name으로 찾아 결과를 페이징 처리하여 반환하는 메소드입니다.
     * @param name 카테고리 이름
     * @param pageable 페이지 객체
     * @return Page<Category>
     */
    Page<Category> findByCategoryNameContaining(String name, Pageable pageable);
}