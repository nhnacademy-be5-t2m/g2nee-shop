package com.t2m.g2nee.shop.review.controller;

import com.t2m.g2nee.shop.review.dto.response.GetBookReviewInfoResponseDto;
import com.t2m.g2nee.shop.review.dto.response.GetBookReviewResponseDto;
import com.t2m.g2nee.shop.review.service.ReviewService;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
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
     * // page 기능 호환 전에 완료 불가
     * @author : 박재희
     * @GetMapping//("/api/reviews/book/{bookId}") public ResponseEntity//pageresponse 필요 productReviewList(Pageable pageable, @PathVariable Long bookId){
     * Page<GetBookReviewResponseDto> reviewDtos = reviewService.getProductReview(pageable, bookId);
     * return ResponseEntity.status(HttpStatus.OK)
     * .contentType(MediaType.APPLICATION_JSON)
     * .body(new reviewDtos);
     * }
     * @since : 1.0
     */

    /**
     * 단일 리뷰 내용 조회
     *
     * @param reviewId 도서 리뷰 번호
     * @return 리뷰 담은 dto
     */
    @GetMapping("/api/review/{reviewId}")
    public ResponseEntity<Optional<GetBookReviewResponseDto>> reviewInfo(@PathVariable Long reviewId) {
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(reviewService.getReview(reviewId));
    }
    /**
     * 회원이 작성한 리뷰 리스트 조회
     *
     * @param
     * @return
     *  */
    // page 객체 완료 전까진 작성 불가
    /**
     * 회원이 리뷰를 작성할 수 있는 상품정보들을 조회하기 위한 메서드입니다.
     * 성공 시 200 반환.
     *
     * @param
     * @param
     * @return 상품정보들이 담긴 Dto 페이지 정보
     */
    // page 필요

    /**
     * 도서의 리뷰 정보를 조회
     * 상품의 리뷰평점, 개수 조회
     * 성공 시 200 반환.
     * QReview
     *
     * @param bookId 도서 정보
     * @return 상품 리뷰 정보들이 담긴 Dto
     */
    @GetMapping("/api/reviews/info/books/{bookId}")
    public ResponseEntity<GetBookReviewInfoResponseDto> reviewBookInfo(
            @PathVariable("bookId") Long bookId) {
        GetBookReviewInfoResponseDto reviewInfo = reviewService.getReviewInfo(bookId);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(reviewInfo);
    }

    /**
     * 리뷰 등록
     * 성공 시 201 반환
     * 회원 권한 필요
     * @
     * @
     */
    //@MemberAuth 권한 어노테이션?
    /**
     * 리뷰 수정
     * 성공 시 201 반환
     * 회원 권한 필요
     * @
     * @
     */
    //@MemberAuth
    /**
     * 리뷰 수정 시 이미지 삭제
     * 성공 시 201 반환
     * 회원 권한 필요
     * @
     * @
     */
    //내용

}
