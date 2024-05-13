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
import org.springframework.web.bind.annotation.RequestBody;
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
@RequestMapping("/api/v1/shop/books")
@RequiredArgsConstructor
public class BookGetController {

    private final BookGetService bookGetService;

    @GetMapping("/{bookId}")
    public ResponseEntity<BookDto.Response> getBookById(@PathVariable("bookId") Long bookId,
                                                        @RequestParam(required = false) Long memberId) {

        BookDto.Response response = bookGetService.getBookDetail(memberId, bookId);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{bookId}/update")
    public ResponseEntity<BookDto.Response> getBookByIdForUpdate(@PathVariable("bookId") Long bookId) {

        BookDto.Response response = bookGetService.getUpdateBook(bookId);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


    /**
     * 가장 최근 출판된 책 8권을 조회하는 컨트롤러 입니다.
     *
     * @return List<BookDto.ListResponse>
     */
    @GetMapping("/new")
    public ResponseEntity<List<BookDto.ListResponse>> getNewBooks() {

        List<BookDto.ListResponse> responses = bookGetService.getNewBooks();

        return ResponseEntity.status(HttpStatus.OK).body(responses);
    }

    /**
     * 관리자 도서 목록에서 사용할 모든 책을 조회하는 컨트롤러 입니다.
     *
     * @param page 페이지 번호
     * @return ResponseEntity<PageResponse < BookDto.ListResponse>>
     */
    @GetMapping("/list")
    public ResponseEntity<PageResponse<BookDto.ListResponse>> getBooks(@RequestParam(defaultValue = "1") int page) {

        PageResponse<BookDto.ListResponse> responses = bookGetService.getAllBook(page);
        return ResponseEntity.status(HttpStatus.OK).body(responses);
    }


    /**
     * 카테고리와 하위 카테고리에 해당하는 책을 조회하는 컨트롤러 입니다.
     *
     * @param memberId   회원 아이디
     * @param page       페이지 번호
     * @param categoryId 카테고리 아이디
     * @return List<BookDto.ListResponse>
     */
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<PageResponse<BookDto.ListResponse>> getBooksByCategory(
            @PathVariable("categoryId") Long categoryId,
            @RequestParam(required = false)
            Long memberId,
            @RequestParam(required = false) String sort,
            @RequestParam int page) {
        if (!StringUtils.hasText(sort)) {
            sort = "viewCount";
        }

        PageResponse<BookDto.ListResponse> responses =
                bookGetService.getBooksByCategory(page, memberId, categoryId, sort);

        return ResponseEntity.status(HttpStatus.OK).body(responses);

    }

    /**
     * Elasticsearch를 이용해서 책을 조회하는 컨트롤러 입니다.
     *
     * @param categoryId 카테고리 아이디
     * @param sort       정렬 기준
     * @param keyword    키워드
     * @param page       페이지 번호
     * @return ResponseEntity<PageResponse < BookDto.ListResponse>>
     */
    @GetMapping("/search")
    public ResponseEntity<PageResponse<BookDto.ListResponse>> getBookByElasticsearchAndCategory(
            @RequestParam(required = false) Long memberId,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) String sort,
            @RequestParam String keyword,
            @RequestParam String condition,
            @RequestParam int page) {

        if (!StringUtils.hasText(sort)) {
            sort = "viewCount";
        }

        PageResponse<BookDto.ListResponse> responses =
                bookGetService.getBookByCategoryAndElasticsearch(page, memberId, categoryId, keyword, sort,condition);

        return ResponseEntity.status(HttpStatus.OK).body(responses);
    }

    /**
     * 카테고리에 맞는 책을 조회하는 컨트롤러
     *
     * @param bookId         책 아이디
     * @param categoryIdList 카테고리 아이디 리스트
     */
    @GetMapping("/{bookId}/recommend")
    public ResponseEntity<List<BookDto.ListResponse>> getRecommendBooks(
            @PathVariable("bookId") Long bookId,
            @RequestParam List<Long> categoryIdList) {

        List<BookDto.ListResponse> responses = bookGetService.getRecommendBooks(categoryIdList, bookId);

        return ResponseEntity.status(HttpStatus.OK).body(responses);
    }

    /**
     * 주문 시 책의 재고 확인을 위해 책 재고를 조회하는 컨트롤러
     *
     * @param bookIdList 책 아이디 리스트
     */
    @GetMapping("/stock")
    public ResponseEntity<List<BookDto.ListResponse>> getBookStock(@RequestParam List<Long> bookIdList) {

        List<BookDto.ListResponse> responses = bookGetService.getBookStock(bookIdList);
        return ResponseEntity.status(HttpStatus.OK).body(responses);
    }
}
