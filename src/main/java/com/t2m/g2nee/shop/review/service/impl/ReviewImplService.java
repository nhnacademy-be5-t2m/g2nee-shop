package com.t2m.g2nee.shop.review.service.impl;

import com.t2m.g2nee.shop.review.dto.request.RequestReviewChangeDto;
import com.t2m.g2nee.shop.review.dto.request.RequestReviewCreateDto;
import com.t2m.g2nee.shop.review.dto.response.ResponseGetMemberReviewDto;
import com.t2m.g2nee.shop.review.dto.response.ResponseGetProductReviewDto;
import com.t2m.g2nee.shop.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
/**
 * 리뷰 service 구현
 *
 * @author : 박재희
 * @since : 1.0
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewImplService implements ReviewService {
    private static final String REVIEWTEXT = "리뷰 작성";

    @Override
    public Page<ResponseGetProductReviewDto> getProductReview(Pageable pageable, Long bookId) {
        return null;
    }

    @Override
    public ResponseGetProductReviewDto getReview(Long reviewId) {
        return null;
    }

    @Override
    public void createReview(RequestReviewCreateDto reviewCreateDto, MultipartFile image) {

    }

    @Override
    public Page<ResponseGetMemberReviewDto> getMemberReviews(Pageable pageable, Long customerId) {
        return null;
    }

    @Override
    public void changeReview(Long reviewId, RequestReviewChangeDto reviewChangeDto, MultipartFile image) {

    }

    @Override
    public void reviewImageDelete(Long reviewId) {

    }
}
