package com.t2m.g2nee.shop.bookset.category.service;


import com.t2m.g2nee.shop.bookset.category.dto.response.CategoryHierarchyDto;
import com.t2m.g2nee.shop.bookset.category.dto.response.CategoryInfoDto;
import com.t2m.g2nee.shop.bookset.category.dto.response.CategoryUpdateDto;
import com.t2m.g2nee.shop.pageUtils.PageResponse;
import java.util.List;

/**
 * 카테고리의 조회를 위한 서비스 입니다.
 *
 * @author : 김수빈
 * @since : 1.0
 */
public interface CategoryQueryService {

    /**
     * 특정 카테고리의 바로 하위 카테고리 리스트 반환합니다.
     *
     * @param categoryId 카테고리 id
     * @return List<CategoryHierarchyDto>
     */
    List<CategoryHierarchyDto> getSubCategories(Long categoryId);

    /**
     * 한 카테고리를 반환합니다.
     *
     * @param categoryId 카테고리 id
     * @return CategoryUpdateDto
     */
    CategoryUpdateDto getCategory(Long categoryId);

    /**
     * 최상위 카테고리만 반환합니다.
     *
     * @return List<CategoryHierarchyDto>
     */
    List<CategoryHierarchyDto> getRootCategories();

    /**
     * 카테고리를 이름으로 검색하여 페이징 처리합니다.
     *
     * @param name 검색할 카테고리 이름
     * @param page 현재 페이지
     * @return PageResponse<CategoryInfoDto>
     */
    PageResponse<CategoryInfoDto> getCategoriesByName(String name, int page);
}
