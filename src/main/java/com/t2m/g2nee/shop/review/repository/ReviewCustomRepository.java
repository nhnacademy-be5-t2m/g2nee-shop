package com.t2m.g2nee.shop.review.repository;

import com.t2m.g2nee.shop.review.dto.ReviewDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReviewCustomRepository {

    Page<ReviewDto.Response> getReviews(Long bookId,Pageable pageable);

    ReviewDto.Response getReview(Long memberId, Long bookId);
}
