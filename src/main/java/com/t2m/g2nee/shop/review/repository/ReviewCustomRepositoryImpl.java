package com.t2m.g2nee.shop.review.repository;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.t2m.g2nee.shop.bookset.book.domain.Book;
import com.t2m.g2nee.shop.bookset.book.domain.QBook;
import com.t2m.g2nee.shop.bookset.book.dto.BookDto;
import com.t2m.g2nee.shop.bookset.book.repository.BookCustomRepository;
import com.t2m.g2nee.shop.bookset.bookcategory.domain.QBookCategory;
import com.t2m.g2nee.shop.bookset.bookcontributor.domain.QBookContributor;
import com.t2m.g2nee.shop.bookset.bookcontributor.dto.BookContributorDto;
import com.t2m.g2nee.shop.bookset.booktag.domain.QBookTag;
import com.t2m.g2nee.shop.bookset.category.domain.QCategory;
import com.t2m.g2nee.shop.bookset.category.dto.response.CategoryInfoDto;
import com.t2m.g2nee.shop.bookset.categoryPath.domain.QCategoryPath;
import com.t2m.g2nee.shop.bookset.contributor.domain.QContributor;
import com.t2m.g2nee.shop.bookset.publisher.domain.QPublisher;
import com.t2m.g2nee.shop.bookset.role.domain.QRole;
import com.t2m.g2nee.shop.bookset.tag.domain.QTag;
import com.t2m.g2nee.shop.bookset.tag.dto.TagDto;
import com.t2m.g2nee.shop.elasticsearch.BooksIndex;
import com.t2m.g2nee.shop.fileset.bookfile.domain.BookFile;
import com.t2m.g2nee.shop.fileset.bookfile.domain.QBookFile;
import com.t2m.g2nee.shop.fileset.file.domain.QFile;
import com.t2m.g2nee.shop.fileset.reviewfile.domain.QReviewFile;
import com.t2m.g2nee.shop.memberset.Member.domain.QMember;
import com.t2m.g2nee.shop.review.domain.QReview;
import com.t2m.g2nee.shop.review.domain.Review;
import com.t2m.g2nee.shop.review.dto.ReviewDto;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
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
