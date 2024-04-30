package com.t2m.g2nee.shop.review.service;

import com.t2m.g2nee.shop.bookset.book.domain.Book;
import com.t2m.g2nee.shop.bookset.book.repository.BookRepository;
import com.t2m.g2nee.shop.exception.NotFoundException;
import com.t2m.g2nee.shop.fileset.reviewfile.domain.ReviewFile;
import com.t2m.g2nee.shop.fileset.reviewfile.repository.ReviewFileRepository;
import com.t2m.g2nee.shop.memberset.member.domain.Member;
import com.t2m.g2nee.shop.memberset.member.repository.MemberRepository;
import com.t2m.g2nee.shop.nhnstorage.AuthService;
import com.t2m.g2nee.shop.nhnstorage.ObjectService;
import com.t2m.g2nee.shop.pageutils.PageResponse;
import com.t2m.g2nee.shop.review.domain.Review;
import com.t2m.g2nee.shop.review.dto.ReviewDto;
import com.t2m.g2nee.shop.review.repository.ReviewRepository;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;


/**
 * 리뷰 service 클래스
 *
 * @author : 신동민
 * @since : 1.0
 */
@Service
@Transactional
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final BookRepository bookRepository;
    private final MemberRepository memberRepository;
    private final ObjectService objectService;
    private final AuthService authService;
    private final ReviewFileRepository reviewFileRepository;

    /**
     * 리뷰를 등록하는 메서드
     *
     * @param image   이미지
     * @param request 리뷰 정보 객체
     * @return ReviewDto.Response
     */
    public ReviewDto.Response postReview(MultipartFile image, ReviewDto.Request request) {

        Book book = bookRepository.findById(request.getBookId()).orElseThrow(() -> new NotFoundException("책 정보가 없습니다"));
        Member member = memberRepository.findById(request.getMemberId())
                .orElseThrow(() -> new NotFoundException("회원 정보가 없습니다"));

        Review review = Review.builder()
                .content(request.getContent())
                .score(request.getScore())
                .member(member)
                .book(book)
                .createdAt(LocalDateTime.now())
                .build();

        Review saveReview = reviewRepository.save(review);

        // 이미지가 있을 경우에만 업로드
        String url = null;
        if (image != null) {
            String tokenId = authService.requestToken();
            url = uploadImage(image, saveReview, tokenId, book.getEngTitle(), member.getCustomerId());
        }

        return ReviewDto.Response.builder()
                .reviewId(saveReview.getReviewId())
                .content(saveReview.getContent())
                .imageUrl(url)
                .score(saveReview.getScore())
                .nickname(saveReview.getMember().getNickname())
                .createdAt(saveReview.getCreatedAt())
                .modifiedAt(saveReview.getModifiedAt())
                .build();
    }

    /**
     * 리뷰 수정 메서드
     *
     * @param request 리뷰 정보 객체
     * @return ReviewDto.Response
     */
    public ReviewDto.Response updateReview(ReviewDto.Request request) {

        Review review = reviewRepository.findById(request.getReviewId())
                .orElseThrow(() -> new NotFoundException("리뷰 정보가 없습니다."));
        review.setModifiedAt(LocalDateTime.now());

        //   이미지 파일 변경이 있을 때 실행
//        if (image != null) {
//            // storage 사용을 위한 토큰을 발급합니다.
//            String tokenId = authService.requestToken();
//
//            // 기존 이미지를 삭제합니다
//            ReviewFile reviewFile = reviewFileRepository.findByReviewId(review.getReviewId());
//            String imageUrl = reviewFile.getUrl();
//            deleteImage(imageUrl, tokenId);
//            // 기존 이미지의 연관관계를 없앱니다.
//            reviewFileRepository.deleteByReviewId(review.getReviewId());
//            // storage에 새 이미지를 업로드합니다
//            uploadImage(image, review, tokenId, review.getBook().getEngTitle(), review.getMember().getCustomerId());
//        }
        Optional.of(request.getScore()).ifPresent(review::setScore);
        Optional.ofNullable(request.getContent()).ifPresent(review::setContent);

        Review modifiedReview = reviewRepository.save(review);

        return ReviewDto.Response.builder()
                .reviewId(modifiedReview.getReviewId())
                .score(modifiedReview.getScore())
                .content(modifiedReview.getContent())
                .nickname(modifiedReview.getMember().getNickname())
                .createdAt(modifiedReview.getCreatedAt())
                .modifiedAt(modifiedReview.getModifiedAt())
                .build();
    }


    /**
     * 리뷰 중복 작성을 막기 위해 해당하는 리뷰를 찾는 메서드
     *
     * @param memberId 회원 아이디
     * @param bookId   책 아이디
     * @return ReviewDto.Response
     */
    public ReviewDto.Response getReview(Long memberId, Long bookId) {
        return reviewRepository.getReview(memberId, bookId);
    }

    /**
     * 책에 대한 리뷰를 5개씩 페이징 조회하는 메서드
     *
     * @param bookId 책 아이디
     * @param page   페이지 번호
     * @return PageResponse<ReviewDto.Response>
     */
    @Transactional(readOnly = true)
    public PageResponse<ReviewDto.Response> getReviews(Long bookId, int page) {

        int size = 5;
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by("createdAt"));

        Page<ReviewDto.Response> reviewPage = reviewRepository.getReviews(bookId, pageable);

        PageResponse<ReviewDto.Response> pageResponse = new PageResponse<>();

        return pageResponse.getPageResponse(page, 4, reviewPage);

    }

    /**
     * 리뷰 이미지 업로드 메서드
     *
     * @param file     이미지
     * @param review   리뷰 객체
     * @param tokenId  storage token
     * @param engTitle 도서 영문명
     * @param memberId 회원아이디
     */
    private String uploadImage(MultipartFile file, Review review, String tokenId, String engTitle, Long memberId) {

        try {
            // 파일을 업로드합니다. 이때 url은 /review/member/{memberId}/도서영문명 으로 저장됩니다.
            InputStream inputStream = file.getInputStream();
            String url = objectService.uploadObject(tokenId, "review/member/" + memberId, engTitle, inputStream);
            // 리뷰 연관 관계를 설정합니다.
            saveReviewFile(url, review);

            return url;
        } catch (IOException e) {
            throw new NotFoundException("파일을 찾을 수 없습니다.");
        }
    }


    /**
     * 리뷰연관 관계를 설정하는 메서드
     *
     * @param url    이미지 url
     * @param review 리뷰 객체
     */
    private void saveReviewFile(String url, Review review) {

        ReviewFile reviewFile = ReviewFile.builder()
                .url(url)
                .type("review")
                .review(review)
                .build();

        reviewFileRepository.save(reviewFile);

    }

    /**
     * 이미지 삭제 메서드
     * @param url 이미지 url
     * @param tokenId storage tokenId
     */
//    private void deleteImage(String url, String tokenId) {
//
//        objectService.deleteObject(url, tokenId);
//    }
}

