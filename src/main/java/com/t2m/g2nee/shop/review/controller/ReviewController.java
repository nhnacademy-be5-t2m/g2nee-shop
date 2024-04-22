package com.t2m.g2nee.shop.review.controller;

import com.t2m.g2nee.shop.pageUtils.PageResponse;
import com.t2m.g2nee.shop.review.dto.ReviewDto;
import com.t2m.g2nee.shop.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/shop/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping
    public ResponseEntity<ReviewDto.Response> postReview(@RequestPart MultipartFile image,
                                                         @RequestPart ReviewDto.Request request) {

        ReviewDto.Response response = reviewService.postReview(image, request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PatchMapping("/{reviewId}")
    public ResponseEntity<ReviewDto.Response> updateReview(@PathVariable("reviewId") Long reviewId,
                                                           @RequestPart ReviewDto.Request request,
                                                           @RequestPart(required = false) MultipartFile image) {

        request.setReviewId(reviewId);
        ReviewDto.Response response = reviewService.updateReview(image, request);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/book/{bookId}")
    public ResponseEntity<PageResponse<ReviewDto.Response>> getReviews(@PathVariable("bookId") Long bookId,
                                                         @RequestParam(defaultValue = "1") int page) {

        PageResponse<ReviewDto.Response> responses = reviewService.getReviews(bookId, page);

        return ResponseEntity.status(HttpStatus.OK).body(responses);
    }
}
