package com.t2m.g2nee.shop.bookset.publisher.controller;

import com.t2m.g2nee.shop.bookset.publisher.domain.Publisher;
import com.t2m.g2nee.shop.bookset.publisher.dto.PublisherDto;
import com.t2m.g2nee.shop.bookset.publisher.service.PublisherService;
import com.t2m.g2nee.shop.pageUtils.PageResponse;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
@RequestMapping("/api/v1/shop/publishers")
@RequiredArgsConstructor
public class PublisherController {

    private final PublisherService publisherService;

    /**
     * 출판사를 생성하는 컨트롤러 입니다.
     *
     * @param request 출판사 정보가 담긴 객체
     * @return 생성한 출판사 정보
     */
    @PostMapping
    public ResponseEntity<PublisherDto.Response> postPublisher(@RequestBody @Valid PublisherDto.Request request) {

        Publisher publisher = Publisher.builder()
                .publisherName(request.getPublisherName())
                .publisherEngName(request.getPublisherEngName())
                .isActivated(true)
                .build();

        PublisherDto.Response response = publisherService.registerPublisher(publisher);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * 출판사 정보를 수정하는 컨트롤러 입니다.
     *
     * @param request     수정할 출판사 정보가 담긴 객체
     * @param publisherId 수정할 출판사 id
     * @return 수정 후 출판사 객체 정보
     */
    @PatchMapping("/{publisherId}")
    public ResponseEntity<PublisherDto.Response> modifyPublisher(@RequestBody @Valid PublisherDto.Request request,
                                                                 @PathVariable("publisherId") Long publisherId) {

        Publisher publisher = Publisher.builder()
                .publisherId(publisherId)
                .publisherName(request.getPublisherName())
                .publisherEngName(request.getPublisherEngName())
                .build();

        PublisherDto.Response response = publisherService.updatePublisher(publisher);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    /**
     * 출판사 페이지를 보여주는 컨트롤러 입니다
     *
     * @param page 페이지 값
     * @return 출판사 정보들과 페이지 정보
     */
    @GetMapping
    public ResponseEntity<PageResponse<PublisherDto.Response>> getPublishers(@RequestParam int page) {

        PageResponse<PublisherDto.Response> response = publisherService.getPublisherList(page);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    /**
     * 출판사 리스트를 보여주는 컨트롤러 입니다
     *
     * @return ResponseEntity<List < PublisherDto.Response>>
     */
    @GetMapping("/list")
    public ResponseEntity<List<PublisherDto.Response>> getPublishers() {

        List<PublisherDto.Response> response = publisherService.getAllPublisher();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


    /**
     * 출판사를 삭제하는 컨트롤러 입니다.
     *
     * @param publisherId 출판사 아이디
     * @return X
     */

    @DeleteMapping("/{publisherId}")
    public ResponseEntity deletePublisher(@PathVariable("publisherId") Long publisherId) {

        publisherService.deletePublisher(publisherId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
