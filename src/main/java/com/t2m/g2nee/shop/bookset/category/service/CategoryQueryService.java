package com.t2m.g2nee.shop.bookset.category.service;


import com.t2m.g2nee.shop.bookset.category.dto.response.CategoryHierarchyDto;
import com.t2m.g2nee.shop.bookset.category.dto.response.CategoryInfoDto;
import com.t2m.g2nee.shop.pageUtils.PageResponse;
import java.util.List;

/**
 * Categories 테이블의 기본적인 생성, 수정, 삭제를 위한 BasicService
 *
 * @author : 김수빈
 * @since : 1.0
 * Categories 테이블의 Select관련 쿼리
 */
public interface CategoryQueryService {

    /**
     * 특정 카테고리의 바로 하위 카테고리 리스트 반환
     *
     * @param categoryId
     * @return
     */
    List<CategoryHierarchyDto> getSubCategories(Long categoryId);

    /**
     * 특정 카테고리 한 개
     *
     * @param categoryId
     * @return
     */
    CategoryHierarchyDto getCategory(Long categoryId);

    /**
     * 최상위 카테고리만 반환
     *
     * @return
     */
    List<CategoryHierarchyDto> getRootCategories();

    /**
     * 전체 카테고리를 반환
     *
     * @return
     */
    List<CategoryInfoDto> getAllCategories();

    /**
     * 카테고리를 이름으로 검색하여 페이징 처리
     *
     * @param name
     * @param page
     * @return
     */
    PageResponse<CategoryInfoDto> getCategoriesByName(String name, int page);
}
