package com.t2m.g2nee.shop.review.service;

import com.t2m.g2nee.shop.review.dto.request.ReviewChangeRequestDto;
import com.t2m.g2nee.shop.review.dto.request.ReviewCreateRequestDto;
import com.t2m.g2nee.shop.review.dto.response.GetBookReviewInfoResponseDto;
import com.t2m.g2nee.shop.review.dto.response.GetBookReviewResponseDto;
import com.t2m.g2nee.shop.review.dto.response.GetMemberReviewResponseDto;
import java.util.Optional;
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
    void createReview(ReviewCreateRequestDto reviewCreateDto, MultipartFile image);

    /**
     * 도서 리뷰 리스트 page 조회
     *
     * @param pageable 페이지 정보, bookId 도서
     * @return 리뷰 dto 담은 페이지
     */
    Page<GetBookReviewResponseDto> getBookReview(Pageable pageable, Long bookId);

    /**
     * 도서 리뷰 단일 상세 조회
     *
     * @param reviewId 리뷰 번호
     * @return 리뷰 dto
     */
    Optional<GetBookReviewResponseDto> getReview(Long reviewId);

    /**
     * 도서 리뷰 정보 간단 조회
     *
     * @param bookId 도서 번호
     * @return 해당 상품의 리뷰 요약 정보 가진 dto
     */
    GetBookReviewInfoResponseDto getReviewInfo(Long bookId);

    /**
     * 회원이 작성한 리뷰 목록 조회
     *
     * @author : 박재희
     * @since : 1.0
     */
    Page<GetMemberReviewResponseDto> getMemberReviews(Pageable pageable, Long customerId);

    /**
     * 리뷰 내용을 수정
     *
     * @param reviewChangeDto 리뷰 수정 데이터 가진 dto
     */
    void changeReview(Long reviewId, ReviewChangeRequestDto reviewChangeDto, MultipartFile image);

    /**
     * 리뷰 수정하며 이미지 삭제
     *
     * @param reviewId 이미지 삭제할 리뷰 번호
     */
    void reviewImageDelete(Long reviewId);


}
