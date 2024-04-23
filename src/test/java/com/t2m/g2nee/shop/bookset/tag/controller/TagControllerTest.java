package com.t2m.g2nee.shop.bookset.tag.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.t2m.g2nee.shop.bookset.tag.domain.Tag;
import com.t2m.g2nee.shop.bookset.tag.dto.TagDto;
import com.t2m.g2nee.shop.bookset.tag.mapper.TagMapper;
import com.t2m.g2nee.shop.bookset.tag.service.TagService;
import com.t2m.g2nee.shop.pageUtils.PageResponse;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@WebMvcTest(TagController.class)
class TagControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TagService tagService;

    @MockBean
    private TagMapper mapper;

    private final String url = "/api/v1/shop/tags/";

    @Test
    @DisplayName("유효성 검사 실패 후 응답값 테스트")
    void testKorValidation() throws Exception {

        TagDto.Request request = TagDto.Request.builder()
                .tagName(" ")
                .build();

        String requestJson = objectMapper.writeValueAsString(request);

        when(tagService.registerTag(any(Tag.class))).thenReturn(
                TagDto.Response.class.newInstance());

        ResultActions resultActions = mockMvc.perform(post(url)
                .content(requestJson)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        resultActions.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(400));
    }

    @Test
    @DisplayName("태그 생성 컨트롤러 테스트")
    void testPostTag() throws Exception {

        // given
        TagDto.Request request = getRequest();
        TagDto.Response response = getResponse();

        String requestJson = objectMapper.writeValueAsString(request);

        when(tagService.registerTag(any(Tag.class))).thenReturn(response);

        //when
        ResultActions resultActions = mockMvc.perform(post(url)
                .content(requestJson)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        //then
        resultActions.andExpect(status().isCreated())
                .andExpect(jsonPath("$.tagName").value(request.getTagName()));
    }

    @Test
    @DisplayName("태그 수정 컨트롤러 테스트")
    void testUpdateTag() throws Exception {

        //given
        TagDto.Request request = getModifyRequest();
        ;
        TagDto.Response modifyresponse = getModifiedResponse();
        Tag tag = getTag();

        String requestJson = objectMapper.writeValueAsString(request);

        when(tagService.updateTag(any(Tag.class))).thenReturn(modifyresponse);

        //when  //then
        ResultActions resultActions = mockMvc.perform(patch(url + tag.getTagId())
                .content(requestJson)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));


        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tagName").value(request.getTagName()));
    }

    @Test
    @DisplayName("태그 리스트 조회 컨트롤러 테스트")
    void testGetTagList() throws Exception {

        //given
        List<Tag> tagList = getList();
        List<TagDto.Response> responses = getResponseList();
        int page = 1;
        int size = 10;
        int totalPage = (int) Math.ceil((double) tagList.size() / size);
        PageResponse<TagDto.Response> pageResponse = PageResponse.<TagDto.Response>builder()
                .data(responses)
                .totalPage(totalPage)
                .currentPage(page)
                .build();

        when(tagService.getTagList(page)).thenReturn(pageResponse);
        when(mapper.entitiesToDtos(tagList)).thenReturn(responses);

        //when
        ResultActions resultActions = mockMvc.perform(get(url)
                .param("page", String.valueOf(page))
                .param("size", String.valueOf(size))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        //then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.currentPage").value(page))
                .andExpect(jsonPath("$.totalPage").value(totalPage));

        for (int i = 0; i < responses.size(); i++) {
            resultActions.andExpect(
                    jsonPath("$.data[" + i + "].tagName").value(responses.get(i).getTagName()));
        }

    }


    @Test
    @DisplayName("태그 삭제 컨트롤러 테스트")
    void deleteTagTest() throws Exception {

        //given
        Tag tag = getTag();


        doNothing().when(tagService).deleteTag(tag.getTagId());

        //when
        mockMvc.perform(delete(url + tag.getTagId()))
                .andExpect(status().isNoContent());

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
