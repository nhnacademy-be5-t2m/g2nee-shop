package com.t2m.g2nee.shop.review.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 도서 리뷰를 확인하는 DTO입니다.
 *
 * @author : 박재희
 * @since : 1.0
 */
@Getter
@AllArgsConstructor
public class GetBookReviewResponseDto {

    private Long reviewId;
    private String nickname;
    private Integer score;
    private String content;
}
