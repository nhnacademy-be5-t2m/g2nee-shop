package com.t2m.g2nee.shop.review.controller;

import com.t2m.g2nee.shop.pageutils.PageResponse;
import com.t2m.g2nee.shop.review.dto.ReviewDto;
import com.t2m.g2nee.shop.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * 리뷰 controller 클래스
 *
 * @author : 신동민
 * @since : 1.0
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/shop/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    /**
     * 리뷰 등록 컨트롤러
     *
     * @param image   이미지
     * @param request 리뷰 정보 객체
     * @return ResponseEntity<ReviewDto.Response>
     */
    @PostMapping
    public ResponseEntity<ReviewDto.Response> postReview(@RequestPart(required = false) MultipartFile image,
                                                         @RequestPart ReviewDto.Request request) {

        ReviewDto.Response response = reviewService.postReview(image, request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * 리뷰 수정 컨트롤러
     *
     * @param request 리뷰 정보 객체
     * @return ResponseEntity<ReviewDto.Response>
     */
    @PatchMapping
    public ResponseEntity<ReviewDto.Response> updateReview(@RequestBody ReviewDto.Request request) {

        ReviewDto.Response response = reviewService.updateReview(request);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    /**
     * 리뷰 하나를 조회하는 컨트롤러
     *
     * @param memberId 회원아이디
     * @param bookId   책 아이디
     * @param request  리뷰 정보 객체
     * @return ResponseEntity<ReviewDto.Response>
     * 확인 용이기 떄문에 응답에 id 값만 있음
     */
    @GetMapping
    public ResponseEntity<ReviewDto.Response> getReview(@RequestParam Long memberId,
                                                        @RequestParam Long bookId) {

        ReviewDto.Response response = reviewService.getReview(memberId, bookId);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    /**
     * 책에 대한 리뷰 조회 컨트롤러
     *
     * @param bookId 책 아이디
     * @param page   페이지 번호
     * @return ResponseEntity<PageResponse < ReviewDto.Response>>
     */
    @GetMapping("/book/{bookId}")
    public ResponseEntity<PageResponse<ReviewDto.Response>> getReviews(@PathVariable("bookId") Long bookId,
                                                                       @RequestParam(defaultValue = "1") int page) {

        PageResponse<ReviewDto.Response> responses = reviewService.getReviews(bookId, page);

        return ResponseEntity.status(HttpStatus.OK).body(responses);
    }
}
