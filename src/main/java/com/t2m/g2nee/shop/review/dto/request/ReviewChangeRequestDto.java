package com.t2m.g2nee.shop.review.dto.request;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 도서 리뷰 정보를 수정하는 DTO입니다.
 *
 * @author : 박재희
 * @since : 1.0
 */
@Getter
@NoArgsConstructor
public class ReviewChangeRequestDto {
    @NotNull(message = "별점을 입력하십시오")
    @Min(value = 1, message = "최소 1점이어야 합니다")
    @Max(value = 5, message = "5점 이햐여야 합니다")
    private Integer score;
    @NotBlank(message = "리뷰를 입력해주십시오")
    @Size(max = 1000, message = "내용은 1000자 이하로 입력하십시오")
    private String content;
}
