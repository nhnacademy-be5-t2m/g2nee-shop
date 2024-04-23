package com.t2m.g2nee.shop.like.controller;

import com.t2m.g2nee.shop.like.dto.BookLikeDto;
import com.t2m.g2nee.shop.like.service.BookLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/shop/likes")
public class BookLikeController {

    private final BookLikeService bookLikeService;

    @PostMapping
    public ResponseEntity<BookLikeDto> addBookLike(@RequestBody BookLikeDto request){

        BookLikeDto response = bookLikeService.addBookLike(request);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{likeId}")
    public ResponseEntity deleteBookLike(@PathVariable("likeId") Long likeId){

        bookLikeService.deleteBookLike(likeId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
