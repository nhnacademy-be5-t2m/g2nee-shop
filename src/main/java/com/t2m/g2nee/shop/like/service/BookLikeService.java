package com.t2m.g2nee.shop.like.service;

import com.t2m.g2nee.shop.bookset.book.domain.Book;
import com.t2m.g2nee.shop.bookset.book.repository.BookRepository;
import com.t2m.g2nee.shop.exception.NotFoundException;
import com.t2m.g2nee.shop.like.domain.BookLike;
import com.t2m.g2nee.shop.like.dto.BookLikeDto;
import com.t2m.g2nee.shop.like.repository.BookLikeRepository;
import com.t2m.g2nee.shop.memberset.Member.domain.Member;
import com.t2m.g2nee.shop.memberset.Member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 좋아요 service 클래스
 *
 * @author : 신동민
 * @since : 1.0
 */
@Service
@RequiredArgsConstructor
@Transactional
public class BookLikeService {

    private final BookLikeRepository bookLikeRepository;
    private final BookRepository bookRepository;
    private final MemberRepository memberRepository;

    /**
     * 책에 좋아요를 설정하는 메서드
     * @param request 책과 회원 정보가 아이디가 담긴 객체
     * @return BookLikeDto
     */
    public BookLikeDto addBookLike(BookLikeDto request) {

        Member member = memberRepository.findById(request.getMemberId())
                .orElseThrow(() -> new NotFoundException("존재하지 않은 회원입니다."));

        Book book = bookRepository.findById(request.getBookId())
                .orElseThrow(() -> new NotFoundException("책 정보가 없습니다."));

        BookLike bookLike = BookLike.builder()
                .book(book)
                .member(member)
                .build();

        BookLike saveBookLike = bookLikeRepository.save(bookLike);

        return BookLikeDto.builder()
                .bookId(saveBookLike.getBook().getBookId())
                .memberId(saveBookLike.getMember().getCustomerId())
                .build();
    }

    public void deleteBookLike(Long likeId) {

        bookLikeRepository.deleteById(likeId);
    }
}
