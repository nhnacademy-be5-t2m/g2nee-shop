package com.t2m.g2nee.shop.review.repository.impl;

import com.t2m.g2nee.shop.review.domain.Review;
import com.t2m.g2nee.shop.review.dto.request.RequestReviewChangeDto;
import com.t2m.g2nee.shop.review.dto.request.RequestReviewCreateDto;
import com.t2m.g2nee.shop.review.dto.response.ResponseGetMemberReviewDto;
import com.t2m.g2nee.shop.review.dto.response.ResponseGetProductReviewDto;
import com.t2m.g2nee.shop.review.repository.ReviewCustomRepository;
import com.t2m.g2nee.shop.review.repository.ReviewRepository;
import com.t2m.g2nee.shop.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

/**
 * 리뷰 repository 구현
 *
 * @author : 박재희
 * @since : 1.0
 */

public class ReviewImplRepository extends QuerydslRepositorySupport
        implements ReviewCustomRepository {
    public ReviewImplRepository(){
        super(Review.class);
    }


    @Override
    public Page<ResponseGetProductReviewDto> findProductReviews(Pageable pageable, Long bookId) {
        return null;
    }

    @Override
    public Page<ResponseGetMemberReviewDto> findMemberReview(Pageable pageable, Long customerId) {
        return null;
    }

    @Override
    public Optional<ResponseGetMemberReviewDto> findReview(Long reviewId) {
        return Optional.empty();
    }
}
