package com.t2m.g2nee.shop.bookset.category.service;


import com.t2m.g2nee.shop.bookset.category.dto.response.CategoryInfoDto;
import com.t2m.g2nee.shop.pageUtils.PageResponse;
import java.util.List;

/**
 * Categories 테이블의 Select관련 쿼리
 */
public interface CategoryQueryService {

    //categoryId에 해당하는 카테고리의 바로 1단계 아래 서브 카테고리 리스트
    PageResponse<CategoryInfoDto> getSubCategories(Long categoryId, int page);

    //categoryId에 해당하는 카테고리
    CategoryInfoDto getCategory(Long categoryId);

    //최상위 카테고리만 출력
    List<CategoryInfoDto> getRootCategories();

    //전체 카테고리 출력
    PageResponse<CategoryInfoDto> getAllCategories(int page);

    //카테고리 이름으로 출력
    PageResponse<CategoryInfoDto> getCategoriesByName(String name, int page);
}
