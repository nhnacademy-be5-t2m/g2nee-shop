package com.t2m.g2nee.shop.like.controller;

import com.t2m.g2nee.shop.like.dto.BookLikeDto;
import com.t2m.g2nee.shop.like.service.BookLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 좋아요 controller 클래스
 *
 * @author : 신동민
 * @since : 1.0
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/shop/likes")
public class BookLikeController {

    private final BookLikeService bookLikeService;

    /**
     * 좋아요 설정, 해제 하는 컨트롤러
     *
     * @param request 정보가 담긴 객체
     * @return ResponseEntity<BookLikeDto>
     */
    @PutMapping
    public ResponseEntity<BookLikeDto> setBookLike(@RequestBody BookLikeDto request) {

        BookLikeDto response = bookLikeService.setBookLike(request);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    /**
     * 회원의 좋아요한 개수를 가져오는 컨트롤러
     *
     * @param memberId 회원 아이디
     * @return ResponseEntity<Long>
     */
    @GetMapping("/member/{memberId}")
    public ResponseEntity<Long> getMemberLikesNum(@PathVariable("memberId") Long memberId) {

        Long response = bookLikeService.getMemberLikesNum(memberId);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
