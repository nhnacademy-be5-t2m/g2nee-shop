package com.t2m.g2nee.shop.review.service;

import com.t2m.g2nee.shop.review.dto.request.RequestReviewChangeDto;
import com.t2m.g2nee.shop.review.dto.request.RequestReviewCreateDto;
import com.t2m.g2nee.shop.review.dto.response.ResponseGetMemberReviewDto;
import com.t2m.g2nee.shop.review.dto.response.ResponseGetProductReviewDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

/**
 * 도서 리뷰 서비스 인터페이스
 *
 * @author : 박재희
 * @since : 1.0
 */
public interface ReviewService {
    /**
     * 도서 리뷰 리스트 조회
     *
     * @author : 박재희
     * @since : 1.0
     */
    Page<ResponseGetProductReviewDto> getProductReview(Pageable pageable, Long bookId);

    /**
     * 도서 리뷰 단일 상세 조회
     *
     * @author : 박재희
     * @since : 1.0
     */
    ResponseGetProductReviewDto getReview(Long reviewId);

    /**
     * 도서 리뷰 단일 상세 조회
     *
     * @author : 박재희
     * @since : 1.0
     */

    /**
     * 리뷰를 생성(이미지 추가 가능)
     *
     * @author : 박재희
     * @since : 1.0
     */
    void createReview(RequestReviewCreateDto reviewCreateDto, MultipartFile image);

    /**
     * 리뷰 내용을 수정
     *
     * @author : 박재희
     * @since : 1.0
     */
    void changeReview(Long reviewId, RequestReviewChangeDto reviewChangeDto, MultipartFile image);

    /**
     * 리뷰 수정하며 이미지 삭제
     *
     * @author : 박재희
     * @since : 1.0
     */
    void reviewImageDelete(Long reviewId);
    /**
     * 회원이 작성한 리뷰 목록 조회
     *
     * @author : 박재희
     * @since : 1.0
     */
    Page<ResponseGetMemberReviewDto> getMemberReviews(Pageable pageable, Long customerId);





}
