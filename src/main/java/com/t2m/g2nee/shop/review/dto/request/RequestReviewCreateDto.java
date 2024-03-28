package com.t2m.g2nee.shop.review.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

/**
 * 도서 리뷰 정보를 등록하는 DTO입니다.
 *
 * @author : 박재희
 * @since : 1.0
 */
@Getter
@NoArgsConstructor
public class RequestReviewCreateDto {
    @NotNull(message = "회원 id를 입력해주세요")
    private Long customerId;
    @NotNull(message = "도서 id를 입력하시오")
    private Long bookId;
    @NotNull(message = "별점을 입력해주십시오")
    @Min(value = 1, message = "최소 1점이어야 합니다")
    @Max(value = 5, message = "5점 이햐여야 합니다")
    private Integer score;
    @NotBlank(message = "리뷰를 입력해주십시오")
    @Size(max = 1000, message = "내용은 1000자 이하로 입력하십시오")
    private String content;
}
