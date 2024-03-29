package com.t2m.g2nee.shop.review.repository.impl;

import com.t2m.g2nee.shop.review.domain.Review;
import com.t2m.g2nee.shop.review.dto.response.GetBookReviewInfoResponseDto;
import com.t2m.g2nee.shop.review.dto.response.GetBookReviewResponseDto;
import com.t2m.g2nee.shop.review.dto.response.GetMemberReviewResponseDto;
import com.t2m.g2nee.shop.review.repository.ReviewCustomRepository;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

/**
 * 리뷰 repository 구현
 *
 * @author : 박재희
 * @since : 1.0
 */

public class ReviewImplRepository extends QuerydslRepositorySupport
        implements ReviewCustomRepository {
    public ReviewImplRepository() {
        super(Review.class);
    }


    @Override
    public Page<GetBookReviewResponseDto> findBookReviews(Pageable pageable, Long bookId) {
        return null;
    }

    @Override
    public Page<GetMemberReviewResponseDto> findMemberReview(Pageable pageable, Long customerId) {
        return null;
    }

    @Override
    public Optional<GetMemberReviewResponseDto> findReview(Long reviewId) {
        return Optional.empty();
    }

    @Override
    public GetBookReviewInfoResponseDto findReviewInfoByBookId(Long bookId) {
        return Optional.empty();
    }


}
