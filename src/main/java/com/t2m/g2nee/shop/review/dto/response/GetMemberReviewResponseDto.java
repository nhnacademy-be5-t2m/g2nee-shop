package com.t2m.g2nee.shop.review.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 회원이 본인 리뷰를 확인하는 DTO입니다.
 *
 * @author : 박재희
 * @since : 1.0
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class GetMemberReviewResponseDto {
    private Long reviewId;
    private Long bookId;
    private String title;
    private String publisherName;
    private Integer score;
    private String content;

}
