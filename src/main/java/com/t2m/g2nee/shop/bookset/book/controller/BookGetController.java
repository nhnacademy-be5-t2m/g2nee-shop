package com.t2m.g2nee.shop.bookset.book.controller;

import com.t2m.g2nee.shop.bookset.book.dto.BookDto;
import com.t2m.g2nee.shop.bookset.book.service.BookGetService;
import com.t2m.g2nee.shop.pageUtils.PageResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 책 조회 controller 클래스
 *
 * @author : 신동민
 * @since : 1.0
 */
@RestController
@Validated
@RequestMapping("/shop/books")
@RequiredArgsConstructor
public class BookGetController {

    private final BookGetService bookGetService;

    @GetMapping("/{bookId}")
    public ResponseEntity<BookDto.Response> getBookById(@PathVariable("bookId") Long bookId) {

        BookDto.Response response = bookGetService.getBookDetail(bookId);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    /**
     * 가장 최근 출판된 책 8권을 조회하는 컨트롤러 입니다.
     * @return List<BookDto.ListResponse>
     */
    @GetMapping("/new")
    public ResponseEntity<List<BookDto.ListResponse>> getNewBooks(){

        List<BookDto.ListResponse> responses = bookGetService.getNewBooks();

        return ResponseEntity.status(HttpStatus.OK).body(responses);
    }

    /**
     * 카테고리와 하위 카테고리에 해당하는 책을 조회하는 컨트롤러 입니다.
     * @param categoryId 카테고리 아이디
     * @return List<BookDto.ListResponse>
     */
    @GetMapping
    public ResponseEntity<PageResponse<BookDto.ListResponse>> getBooksByCategory(@RequestParam Long categoryId,
                                                                                 @RequestParam(required = false)
                                                                                 String sort,
                                                                         @RequestParam int page){

        if (!StringUtils.hasText(sort)) {
            sort = "viewCount";
        }

        PageResponse<BookDto.ListResponse> responses = bookGetService.getBooksByCategory(page, categoryId,sort);

        return ResponseEntity.status(HttpStatus.OK).body(responses);

    }

    @GetMapping("/search")
    public ResponseEntity<PageResponse<BookDto.ListResponse>> getBookByElasticsearchAndCategory(
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) String sort,
            @RequestParam String keyword,
            @RequestParam int page) {

        if (!StringUtils.hasText(sort)) {
            sort = "viewCount";
        }
        PageResponse<BookDto.ListResponse> responses =
                bookGetService.getBookByCategoryAndElasticsearch(page, categoryId, keyword, sort);

        return ResponseEntity.status(HttpStatus.OK).body(responses);
    }
}
