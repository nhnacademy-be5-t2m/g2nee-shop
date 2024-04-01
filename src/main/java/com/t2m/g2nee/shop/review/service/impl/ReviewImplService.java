package com.t2m.g2nee.shop.review.service.impl;

import com.t2m.g2nee.shop.review.dto.request.ReviewChangeRequestDto;
import com.t2m.g2nee.shop.review.dto.request.ReviewCreateRequestDto;
import com.t2m.g2nee.shop.review.dto.response.GetBookReviewInfoResponseDto;
import com.t2m.g2nee.shop.review.dto.response.GetBookReviewResponseDto;
import com.t2m.g2nee.shop.review.dto.response.GetMemberReviewResponseDto;
import com.t2m.g2nee.shop.review.repository.ReviewRepository;
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
    private final ReviewRepository reviewRepository;
    //BOok repository
    //Member repository
    //review point
    //point history
    //file

    @Override
    public Page<GetBookReviewResponseDto> getBookReview(Pageable pageable, Long bookId) {
        return reviewRepository.findBookReviews(pageable, bookId);
    }

    @Override
    public GetBookReviewResponseDto getReview(Long reviewId) {
        //return reviewRepository.findReview(reviewId);
        return null;
    }

    @Override
    public GetBookReviewInfoResponseDto getReviewInfo(Long bookId) {
        return reviewRepository.findReviewInfoByBookId(bookId);
    }

    @Override
    public void createReview(ReviewCreateRequestDto reviewCreateDto, MultipartFile image) {

    }

    @Override
    public Page<GetMemberReviewResponseDto> getMemberReviews(Pageable pageable, Long customerId) {
        return reviewRepository.findMemberReview(pageable, customerId);
    }

    @Override
    public void changeReview(Long reviewId, ReviewChangeRequestDto reviewChangeDto,
                             MultipartFile image) {
        /*
        Review review = reviewRepository.findById(reviewId)
        .orElseThrow(() -> new ChangeSetPersister.NotFoundException());
         */


    }

    @Override
    @Transactional
    public void reviewImageDelete(Long reviewId) {
        /*
        Review review = reviewRepository.findById(reviewId)

                .orElseThrow(() -> new ChangeSetPersister.NotFoundException());
        if (Objects.nonNull(review.getFile()))
        */
    }
}
