package com.t2m.g2nee.shop.review.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 도서 리뷰 상세를 확인하는 DTO입니다.
 *
 * @author : 박재희
 * @since : 1.0
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseGetProductReviewSummaryDto {
    private Long reviewCount;
    private Integer score;

}

