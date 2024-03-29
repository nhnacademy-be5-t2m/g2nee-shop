package com.t2m.g2nee.shop.review.controller;

import com.t2m.g2nee.shop.review.dto.response.ResponseGetProductReviewDto;
import com.t2m.g2nee.shop.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 리뷰 정보를 다루는 컨트롤러입니다.
 *
 * @author : 박재희
 * @since : 1.0
 */
@RestController
@RequiredArgsConstructor
@RequestMapping
public class ReviewController {
    private ReviewService reviewService;
    /**
     * 리뷰 리스트 조회
     *
     * @author : 박재희
     * @since : 1.0
     */
    @GetMapping//("/api/reviews/book/{bookId}")
    public ResponseEntity/*pageresponse 필요*/ productReviewList(Pageable pageable, @PathVariable Long bookId){
        Page<ResponseGetProductReviewDto> reviewDtos = reviewService.getProductReview(pageable, bookId);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new reviewDtos);
    }
}
