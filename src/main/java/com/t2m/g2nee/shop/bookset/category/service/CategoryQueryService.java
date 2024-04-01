package com.t2m.g2nee.shop.bookset.category.service;


import com.t2m.g2nee.shop.bookset.category.dto.response.CategoryInfoDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Categories 테이블의 Select관련 쿼리
 */
public interface CategoryQueryService {

    //categoryId에 해당하는 카테고리의 바로 1단계 아래 서브 카테고리 리스트
    Page<CategoryInfoDto> getSubCategories(Long categoryId, Pageable pageable);

    //categoryId에 해당하는 카테고리
    CategoryInfoDto getCategory(Long categoryId);

    //최상위 카테고리만 출력
    Page<CategoryInfoDto> getRootCategories(Pageable pageable);

    //전체 카테고리 출력
    Page<CategoryInfoDto> getAllCategories(Pageable pageable);
}
