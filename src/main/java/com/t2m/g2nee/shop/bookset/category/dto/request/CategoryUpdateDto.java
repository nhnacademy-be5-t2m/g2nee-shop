package com.t2m.g2nee.shop.bookset.category.dto.request;


import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 카테고리 업데이트 시 필요한 dto
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryUpdateDto {

    @NotBlank(message = "카테고리 아이디가 없음!")
    private Long categoryId;

    //등록할 카테고리의 이름과 영문 이름
    @Max(value = 50, message = "50자 미만으로 입력해 주세요.")
    @NotBlank(message = "카테고리 명은 비울 수 없습니다.")
    private String categoryName;

    @Max(value = 50, message = "50자 미만으로 입력해 주세요.")
    @NotBlank(message = "카테고리 명은 비울 수 없습니다.")
    private String categoryEngName;

    //수정할 카테고리의 바로 상위 카테고리Id
    @NotBlank(message = "상위 카테고리가 없음!")
    private Long ancestorCategoryId;
}
