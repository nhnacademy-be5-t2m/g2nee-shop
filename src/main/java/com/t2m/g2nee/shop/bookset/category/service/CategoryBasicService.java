package com.t2m.g2nee.shop.bookset.category.service;

import com.t2m.g2nee.shop.bookset.category.domain.Category;
import java.util.List;

/**
 * Categories 테이블의 기본적인 생성, 수정, 삭제를 위한 BasicService
 */
public interface CategoryBasicService {


    //category 저장
    Category saveCategoryBasic(Category category);

    //category 수정
    Category updateCategoryBasic(Category category);

    //category 삭제
    boolean deleteCategoryBasic(Long categoryId);

    List<Category> getAncestorList(Long descendantId);

    Category activeCategory(Long categoryId);
}