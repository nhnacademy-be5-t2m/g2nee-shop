package com.t2m.g2nee.shop.review.repository;

import com.querydsl.core.types.Projections;
import com.t2m.g2nee.shop.fileset.reviewfile.domain.QReviewFile;
import com.t2m.g2nee.shop.memberset.member.domain.QMember;
import com.t2m.g2nee.shop.review.domain.QReview;
import com.t2m.g2nee.shop.review.domain.Review;
import com.t2m.g2nee.shop.review.dto.ReviewDto;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;


public class ReviewCustomRepositoryImpl extends QuerydslRepositorySupport implements ReviewCustomRepository {

    public ReviewCustomRepositoryImpl() {
        super(Review.class);
    }

    /**
     * 책의 리뷰 정보를 조회하는 메서드
     *
     * @param bookId   책아이디
     * @param pageable 페이징 객체
     * @return Page<ReviewDto.Response>
     */
    @Override
    public Page<ReviewDto.Response> getReviews(Long bookId, Pageable pageable) {

        QReview review = QReview.review;
        QMember member = QMember.member;
        QReviewFile reviewFile = QReviewFile.reviewFile;

        List<ReviewDto.Response> reviewList = from(review)
                .innerJoin(member).on(review.member.customerId.eq(member.customerId))
                .innerJoin(reviewFile).on(review.reviewId.eq(reviewFile.review.reviewId))
                .where(review.book.bookId.eq(bookId))
                .select(Projections.fields(ReviewDto.Response.class
                        , review.reviewId
                        , review.content
                        , review.score
                        , reviewFile.url.as("imageUrl")
                        , review.createdAt
                        , review.modifiedAt))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long count = from(review).select(review.count()).fetchOne();

        return new PageImpl<>(reviewList, pageable, count);
    }

    @Override
    public ReviewDto.Response getReview(Long memberId, Long bookId) {

        QReview review = QReview.review;

        return from(review)
                .where(review.member.customerId.eq(memberId)
                        .and(review.book.bookId.eq(bookId)))
                .select(Projections.fields(ReviewDto.Response.class
                        , review.reviewId))
                .fetchOne();
    }
}
