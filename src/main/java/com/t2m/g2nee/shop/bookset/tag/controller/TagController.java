package com.t2m.g2nee.shop.bookset.tag.controller;


import com.t2m.g2nee.shop.bookset.tag.domain.Tag;
import com.t2m.g2nee.shop.bookset.tag.dto.TagDto;
import com.t2m.g2nee.shop.bookset.tag.service.TagService;
import com.t2m.g2nee.shop.pageUtils.PageResponse;
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
@RequestMapping("/shop/tag")
@RequiredArgsConstructor
public class TagController {
    private final TagService tagService;

    /**
     * 태그를 생성하는 컨트롤러 입니다.
     *
     * @param request 태그 정보가 담긴 객체
     * @return 생성한 태그 정보
     */
    @PostMapping
    public ResponseEntity<TagDto.Response> postTag(@RequestBody @Valid TagDto.Request request) {

        Tag tag = Tag.builder()
                .tagName(request.getTagName())
                .build();

        TagDto.Response response = tagService.registerTag(tag);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * 태그 정보를 수정하는 컨트롤러 입니다.
     *
     * @param request 수정할 태그 정보가 담긴 객체
     * @param tagId   수정할 태그 id
     * @return 수정 후 태그 객체 정보
     */
    @PatchMapping("/{tagId}")
    public ResponseEntity<TagDto.Response> modifyTag(@RequestBody @Valid TagDto.Request request,
                                                     @PathVariable("tagId") Long tagId) {

        Tag tag = Tag.builder()
                .tagId(tagId)
                .tagName(request.getTagName())
                .build();

        TagDto.Response response = tagService.updateTag(tag);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    /**
     * 태그 리스트를 보여주는 컨트롤러 입니다
     *
     * @param page 페이지 값
     * @return 태그 정보들과 페이지 정보
     */
    @GetMapping
    public ResponseEntity<PageResponse<TagDto.Response>> getTags(@RequestParam int page) {

        PageResponse<TagDto.Response> response = tagService.getTagList(page);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    /**
     * 태그를 삭제하는 컨트롤러 입니다.
     *
     * @param tagId
     * @return X
     */
    @DeleteMapping("/{tagId}")
    public ResponseEntity deleteTag(@PathVariable("tagId") Long tagId) {

        tagService.deleteTag(tagId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
