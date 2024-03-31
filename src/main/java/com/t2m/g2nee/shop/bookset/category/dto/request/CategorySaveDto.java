package com.t2m.g2nee.shop.bookset.category.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 카테고리 저장을 위한 dto
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CategorySaveDto {

    //등록할 카테고리의 이름과 영문 이름
    private String categoryName;
    private String categoryEngName;

    //틍록할 카테고리의 바로 상위 카테고리Id
    private Long ancestorCategoryId;
}