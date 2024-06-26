package com.t2m.g2nee.shop.bookset.book.controller;

import com.t2m.g2nee.shop.bookset.book.dto.BookDto;
import com.t2m.g2nee.shop.bookset.book.service.BookMgmtService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


/**
 * 책 관리 controller 클래스
 *
 * @author : 신동민
 * @since : 1.0
 */
@RestController
@Validated
@RequestMapping("/api/v1/shop/books")
@RequiredArgsConstructor
public class BookMgmtController {

    private final BookMgmtService bookMgmtService;


    /**
     * 책을 등록하는 컨트롤러 입니다.
     *
     * @param request   책 등록에 필요한 정보가 담긴 객체
     * @param thumbnail 섬네일 이미지 파일
     * @param details   상세 이미지 파일
     * @return
     */
    @PostMapping
    public ResponseEntity<BookDto.Response> postBook(@RequestPart BookDto.Request request,
                                                     @RequestPart MultipartFile thumbnail,
                                                     @RequestPart List<MultipartFile> details
    ) {
        BookDto.Response response = bookMgmtService.registerBook(request, thumbnail, details);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * 등록된 책 정보를 컨트롤러 입니다.
     *
     * @param request   책 등록에 필요한 정보가 담긴 객체
     * @param thumbnail 섬네일 이미지 파일
     * @param details   상세 이미지 파일
     * @return
     */
    @PatchMapping("/{bookId}")
    public ResponseEntity<BookDto.Response> updateBook(@PathVariable("bookId") Long bookId,
                                                       @RequestPart BookDto.Request request,
                                                       @RequestPart(required = false) MultipartFile thumbnail,
                                                       @RequestPart(required = false) MultipartFile[] details) {

        BookDto.Response response = bookMgmtService.updateBook(bookId, request, thumbnail, details);
        return ResponseEntity.status(HttpStatus.OK).body(response);

    }

    /**
     * 책 상태를 변경하는 컨트롤러 입니다.
     *
     * @param bookId  책 아이디
     * @param request 변경할 상태가 담긴 객체
     */
    @PatchMapping("/status/{bookId}")
    public ResponseEntity<BookDto.StatusResponse> modifyBookStatus(@PathVariable("bookId") Long bookId,
                                                                   @RequestBody BookDto.Request request) {

        BookDto.StatusResponse response = bookMgmtService.modifyStatus(bookId, request);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    /**
     * 책 수량을 변경하는 컨트롤러
     *
     * @param bookId   책 아이디
     * @param quantity 책 수량
     */
    @PatchMapping("/quantity/{bookId}")
    public ResponseEntity<Integer> modifyBookQuantity(@PathVariable("bookId") Long bookId,
                                                      @RequestParam("quantity") int quantity) {


        int updateQuantity = bookMgmtService.modifyQuantity(bookId, quantity);

        return ResponseEntity.status(HttpStatus.OK).body(updateQuantity);
    }

}

