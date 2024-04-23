package com.t2m.g2nee.shop.like.controller;

import com.t2m.g2nee.shop.like.dto.BookLikeDto;
import com.t2m.g2nee.shop.like.service.BookLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/shop/likes")
public class BookLikeController {

    private final BookLikeService bookLikeService;

    @PutMapping
    public ResponseEntity<BookLikeDto> setBookLike(@RequestBody BookLikeDto request) {

        BookLikeDto response = bookLikeService.setBookLike(request);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
