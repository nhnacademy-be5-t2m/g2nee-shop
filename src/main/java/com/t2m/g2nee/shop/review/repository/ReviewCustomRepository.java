package com.t2m.g2nee.shop.review.repository;

import com.t2m.g2nee.shop.review.dto.response.GetBookReviewInfoResponseDto;
import com.t2m.g2nee.shop.review.dto.response.GetBookReviewResponseDto;
import com.t2m.g2nee.shop.review.dto.response.GetMemberReviewResponseDto;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * 리뷰 custom repository
 *
 * @author : 박재희
 * @since : 1.0
 */
@NoRepositoryBean
public interface ReviewCustomRepository {
    /**
     * 도서 페이지에서 리뷰 목록 조회
     *
     * @param pageable 페이지
     * @param bookId   도서 번호
     * @return 리뷰 데이터 가진 dto 페이지
     */
    Page<GetBookReviewResponseDto> findBookReviews(Pageable pageable, Long bookId);

    /**
     * 회원이 작성한 리뷰 목록 조회
     *
     * @param pageable   페이지
     * @param customerId 회원 번호
     * @return 회원이 작성한 리뷰 데이터 가진 dto 페이지
     */
    Page<GetMemberReviewResponseDto> findMemberReview(Pageable pageable, Long customerId);

    /**
     * 해당 회원이 상품평 작성 가능한 상품들을 조회해오기 위한 메서드.
     *
     * @param pageable 페이지 정보
     * @param memberNo 회원 번호
     * @return 해당 회원이 상품평 작성 가능한 상품들의 간단한 정보가 담긴 Dto Page 정보
     */
    //TODO1: 회원이 작성가능한 리뷰 가능한 도서 확인하는 메서드 -- 회원이 주문에서 리뷰작성가능한 도서 확인

    /**
     * 리뷰 상세 조회
     *
     * @param reviewId 리뷰 번호
     * @return
     */
    Optional<GetMemberReviewResponseDto> findReview(Long reviewId);

    /**
     * 도서의 리뷰 요약 조회
     *
     * @param bookId 도서 번호
     * @return 해당 도서의 리뷰 요약된 정보가 담긴 dto
     */
    GetBookReviewInfoResponseDto findReviewInfoByBookId(Long bookId);
    //TODO2: 상품에서 리뷰 정보 확인하는 dto 필요
}
