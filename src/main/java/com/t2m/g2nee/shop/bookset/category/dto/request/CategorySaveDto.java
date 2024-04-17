package com.t2m.g2nee.shop.bookset.category.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 카테고리 저장을 위한 dto
 *
 * @author : 김수빈
 * @since : 1.0
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CategorySaveDto {

    //등록할 카테고리의 이름과 영문 이름
    @NotBlank(message = "카테고리 이름은 비울 수 없습니다.")
    @Size(min = 1, max = 50, message = "50자 미만으로 입력해 주세요.")
    @Pattern(regexp = "^[가-힣\\s0-9]+$", message = "카테고리 한글 이름을 입력해주세요.")
    private String categoryName;

    @NotBlank(message = "카테고리 이름은 비울 수 없습니다.")
    @Size(min = 1, max = 50, message = "50자 미만으로 입력해 주세요.")
    @Pattern(regexp = "^[A-Za-z\\s0-9]+$", message = "카테고리 영문 이름을 입력해주세요.")
    private String categoryEngName;

    @NotNull(message = "카테고리 활성 정보가 없음!")
    private Boolean isActivated;

    //틍록할 카테고리의 바로 상위 카테고리Id
    @NotNull(message = "상위 카테고리가 없음!")
    private Long ancestorCategoryId;
}