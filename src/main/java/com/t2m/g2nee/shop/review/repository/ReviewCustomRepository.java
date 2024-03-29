package com.t2m.g2nee.shop.review.repository;

import com.t2m.g2nee.shop.review.dto.response.ResponseGetMemberReviewDto;
import com.t2m.g2nee.shop.review.dto.response.ResponseGetProductReviewDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Optional;

/**
 * 리뷰 custom repository
 *
 * @author : 박재희
 * @since : 1.0
 */
@NoRepositoryBean
public interface ReviewCustomRepository {
    /**
     * 도서 리뷰 목록 조회
     *
     * @param pageable 페이지
     * @param bookId 도서 번호
     * @return 리뷰 데이터 가진 dto 페이지
     */
    Page<ResponseGetProductReviewDto> findProductReviews(Pageable pageable, Long bookId);
    /**
     * 회원 리뷰 목록 조회
     *
     * @param pageable 페이지
     * @param customerId 회원 번호
     * @return 회원이 작성한 리뷰 데이터 가진 dto 페이지
     */
    Page<ResponseGetMemberReviewDto> findMemberReview(Pageable pageable, Long customerId);

    //TODO1: 회원이 작성가능한 리뷰 가능한 도서 확인하는 메서드 -- 회원이 주문에서 리뷰작성가능한 도서 확인
    /**
     * 리뷰 상세 조회
     *
     * @param reviewId 리뷰 번호
     * @return
     */
    Optional<ResponseGetMemberReviewDto> findReview(Long reviewId);
    /**
     * 리뷰 상세 조회
     *
     * @param reviewId 리뷰 번호
     * @return
     */
    //TODO2: 상품에서 리뷰 정보 확인하는 dto 필요
    //TODO3: 관리자한테 리뷰 삭제 권한 주는지 의논
}
