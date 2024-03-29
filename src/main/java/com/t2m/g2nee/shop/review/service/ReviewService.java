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
     * 리뷰를 생성(이미지 추가 가능)
     *
     * @param reviewCreateDto 리뷰 생성에 필요한 정보 가진 dto
     */
    void createReview(RequestReviewCreateDto reviewCreateDto, MultipartFile image);

    /**
     * 도서 리뷰 리스트 page 조회
     *
     * @param pageable 페이지 정보, bookId 도서
     * @return 리뷰 dto 담은 페이지
     */
    Page<ResponseGetProductReviewDto> getProductReview(Pageable pageable, Long bookId);

    /**
     * 도서 리뷰 단일 상세 조회
     *
     * @param reviewId 리뷰 번호
     * @return 리뷰 dto
     */
    ResponseGetProductReviewDto getReview(Long reviewId);

    /**
     * 회원이 작성한 리뷰 목록 조회
     *
     * @author : 박재희
     * @since : 1.0
     */
    Page<ResponseGetMemberReviewDto> getMemberReviews(Pageable pageable, Long customerId);

    /**
     * 리뷰 내용을 수정
     *
     * @param reviewChangeDto 리뷰 수정 데이터 가진 dto
     */
    void changeReview(Long reviewId, RequestReviewChangeDto reviewChangeDto, MultipartFile image);

    /**
     * 리뷰 수정하며 이미지 삭제
     *
     * @param reviewId 이미지 삭제할 리뷰 번호
     */
    void reviewImageDelete(Long reviewId);




}
