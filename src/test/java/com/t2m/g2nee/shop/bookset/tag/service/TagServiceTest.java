package com.t2m.g2nee.shop.bookset.tag.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.t2m.g2nee.shop.bookset.tag.domain.Tag;
import com.t2m.g2nee.shop.bookset.tag.dto.TagDto;
import com.t2m.g2nee.shop.bookset.tag.mapper.TagMapper;
import com.t2m.g2nee.shop.bookset.tag.repository.TagRepository;
import com.t2m.g2nee.shop.exception.NotFoundException;
import com.t2m.g2nee.shop.pageUtils.PageResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@ExtendWith(MockitoExtension.class)
class TagServiceTest {
    @Mock
    private TagRepository tagRepository;
    @Mock
    private TagMapper mapper;

    @InjectMocks
    private TagService tagService;

    @Test
    @DisplayName("태그 등록 테스트")
    void registerTag() {

        //given
        TagDto.Request request = getRequest();
        Tag tag = getTag();
        TagDto.Response response = getResponse();

        when(tagRepository.save(tag)).thenReturn(tag);
        when(mapper.entityToDto(tag)).thenReturn(response);

        //when
        tagService.registerTag(tag);

        // then
        assertEquals(response.getTagName(), request.getTagName());

    }

    @Test
    @DisplayName("태그 수정 테스트")
    void updateTag() {

        //given
        TagDto.Request request = getModifyRequest();
        Tag tag = getModifiedTag();
        TagDto.Response response = getModifiedResponse();

        when(tagRepository.findById(tag.getTagId())).thenReturn(Optional.of(tag));
        when(tagRepository.save(tag)).thenReturn(tag);

        //when
        tagService.updateTag(tag);

        //then
        assertEquals(response.getTagName(), request.getTagName());

    }

    @Test
    @DisplayName("태그 리스트 조회 테스트")
    void getTagList() {

        //given
        List<Tag> tagList = getList();
        List<TagDto.Response> responseList = getResponseList();
        PageResponse<TagDto.Response> responses = PageResponse.<TagDto.Response>builder()
                .data(responseList)
                .currentPage(1)
                .totalPage(2)
                .build();

        Pageable pageable = PageRequest.of(0, 10, Sort.by("tagName"));

        Page<Tag> tagPage = new PageImpl<>(tagList, pageable, tagList.size());

        when(tagRepository.findAll(PageRequest.of(0, 10, Sort.by("tagName"))))
                .thenReturn(tagPage);
        when(mapper.entitiesToDtos(tagList)).thenReturn(responseList);

        //when
        tagService.getTagList(1);

        //then
        assertEquals(10, responses.getData().size());
        assertEquals(1, responses.getCurrentPage());
        assertEquals(2, responses.getTotalPage());
        assertEquals(20, tagPage.getTotalElements());
        for (int i = 0; i < responseList.size(); i++) {
            assertEquals(responseList.get(i).getTagName(), responses.getData().get(i).getTagName());
        }
    }

    @Test
    @DisplayName("출판사 삭제 테스트")
    void deleteTag() {

        //given
        Tag tag = getTag();

        when(tagRepository.findById(tag.getTagId())).thenReturn(Optional.of(tag));
        doNothing().when(tagRepository).deleteById(tag.getTagId());

        //when //then
        tagService.deleteTag(tag.getTagId());

        verify(tagRepository, times(1)).deleteById(tag.getTagId());

    }

    @Test
    @DisplayName("태그가 없을 때 예외 테스트")
    void testExistTag() {
        Tag tag = getTag();

        when(tagRepository.findById(tag.getTagId())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> tagService.deleteTag(tag.getTagId()));

    }

    private List<Tag> getList() {

        List<Tag> tagList = new ArrayList<>();
        for (int i = 1; i < 21; i++) {
            Tag tag = Tag.builder()
                    .tagId((long) i)
                    .tagName("태그" + i)
                    .build();

            tagList.add(tag);
        }
        return tagList;
    }

    private List<TagDto.Response> getResponseList() {

        List<TagDto.Response> tagList = new ArrayList<>();
        for (int i = 1; i < 11; i++) {
            TagDto.Response tag = TagDto.Response.builder()
                    .tagId((long) i)
                    .tagName("태그" + i)

                    .build();

            tagList.add(tag);
        }
        return tagList;
    }

    private TagDto.Request getRequest() {

        return TagDto.Request.builder()
                .tagName("태그1")
                .build();
    }

    private TagDto.Request getModifyRequest() {


        return TagDto.Request.builder()
                .tagName("태그2")
                .build();
    }

    private Tag getTag() {

        return Tag.builder()
                .tagId(1L)
                .tagName("태그1")
                .build();
    }

    private Tag getModifiedTag() {

        return Tag.builder()
                .tagId(1L)
                .tagName("태그2")
                .build();
    }


    private TagDto.Response getResponse() {

        return TagDto.Response.builder()
                .tagId(1L)
                .tagName("태그1")
                .build();
    }

    private TagDto.Response getModifiedResponse() {

        return TagDto.Response.builder()
                .tagId(1L)
                .tagName("태그2")
                .build();
    }
}
