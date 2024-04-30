package com.t2m.g2nee.shop.bookset.publisher.controller;

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
import com.t2m.g2nee.shop.bookset.publisher.domain.Publisher;
import com.t2m.g2nee.shop.bookset.publisher.dto.PublisherDto;
import com.t2m.g2nee.shop.bookset.publisher.mapper.PublisherMapper;
import com.t2m.g2nee.shop.bookset.publisher.service.PublisherService;
import com.t2m.g2nee.shop.pageutils.PageResponse;
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

@WebMvcTest(PublisherController.class)
class PublisherControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PublisherService publisherService;

    @MockBean
    private PublisherMapper mapper;

    private final String url = "/api/v1/shop/publishers/";

    @Test
    @DisplayName("한글 유효성 검사 실패 후 응답값 테스트")
    void testKorValidation() throws Exception {

        PublisherDto.Request request = PublisherDto.Request.builder()
                .publisherName("eng")
                .publisherEngName("eng")
                .build();

        String requestJson = objectMapper.writeValueAsString(request);

        when(publisherService.registerPublisher(any(Publisher.class))).thenReturn(
                PublisherDto.Response.class.newInstance());

        ResultActions resultActions = mockMvc.perform(post(url)
                .content(requestJson)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        resultActions.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(400));
    }

    @Test
    @DisplayName("영문 유효성 검사 실패 후 응답값 테스트")
    void testEngValidation() throws Exception {


        PublisherDto.Request request = PublisherDto.Request.builder()
                .publisherName("한글")
                .publisherEngName("한글")

                .build();

        String requestJson = objectMapper.writeValueAsString(request);

        when(publisherService.registerPublisher(any(Publisher.class))).thenReturn(
                PublisherDto.Response.class.newInstance());

        ResultActions resultActions = mockMvc.perform(post(url)
                .content(requestJson)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        resultActions.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(400));
    }


    @Test
    @DisplayName("출판사 생성 컨트롤러 테스트")
    void testPostPublisher() throws Exception {

        // given
        PublisherDto.Request request = getRequest();
        PublisherDto.Response response = getResponse();

        String requestJson = objectMapper.writeValueAsString(request);

        when(publisherService.registerPublisher(any(Publisher.class))).thenReturn(response);

        //when
        ResultActions resultActions = mockMvc.perform(post(url)
                .content(requestJson)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        //then
        resultActions.andExpect(status().isCreated())
                .andExpect(jsonPath("$.publisherName").value(request.getPublisherName()))
                .andExpect(jsonPath("$.publisherEngName").value(request.getPublisherEngName()));
    }

    @Test
    @DisplayName("출판사 수정 컨트롤러 테스트")
    void testUpdatePublisher() throws Exception {

        //given
        PublisherDto.Request request = getModifyRequest();
        ;
        PublisherDto.Response modifyresponse = getModifiedResponse();
        Publisher publisher = getPublisher();

        String requestJson = objectMapper.writeValueAsString(request);

        when(publisherService.updatePublisher(any(Publisher.class))).thenReturn(modifyresponse);

        //when  //then
        ResultActions resultActions = mockMvc.perform(patch(url + publisher.getPublisherId())
                .content(requestJson)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));


        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.publisherName").value(request.getPublisherName()))
                .andExpect(jsonPath("$.publisherEngName").value(request.getPublisherEngName()));
    }

    @Test
    @DisplayName("출판사 리스트 조회 컨트롤러 테스트")
    void testGetPublisherList() throws Exception {

        //given
        List<Publisher> publisherList = getList();
        List<PublisherDto.Response> responses = getResponseList();
        int page = 1;
        int size = 10;
        int totalPage = (int) Math.ceil((double) publisherList.size() / size);
        PageResponse<PublisherDto.Response> pageResponse = PageResponse.<PublisherDto.Response>builder()
                .data(responses)
                .totalPage(totalPage)
                .currentPage(page)
                .build();

        when(publisherService.getPublisherList(page)).thenReturn(pageResponse);
        when(mapper.entitiesToDtos(publisherList)).thenReturn(responses);

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
                            jsonPath("$.data[" + i + "].publisherName").value(responses.get(i).getPublisherName()))
                    .andExpect(jsonPath("$.data[" + i + "].publisherEngName").value(
                            responses.get(i).getPublisherEngName()));
        }

    }


    @Test
    @DisplayName("출판사 삭제 컨트롤러 테스트")
    void deletePublisherTest() throws Exception {

        //given
        Publisher publisher = getPublisher();


        doNothing().when(publisherService).deletePublisher(publisher.getPublisherId());

        //when
        mockMvc.perform(delete(url + publisher.getPublisherId()))
                .andExpect(status().isNoContent());

    }


    private List<Publisher> getList() {

        List<Publisher> publisherList = new ArrayList<>();
        for (int i = 1; i < 21; i++) {
            Publisher publisher = Publisher.builder()
                    .publisherId((long) i)
                    .publisherName("출판사" + i)
                    .publisherEngName("publisher" + i)
                    .build();

            publisherList.add(publisher);
        }
        return publisherList;
    }

    private List<PublisherDto.Response> getResponseList() {

        List<PublisherDto.Response> publisherList = new ArrayList<>();
        for (int i = 1; i < 11; i++) {
            PublisherDto.Response publisher = PublisherDto.Response.builder()
                    .publisherId((long) i)
                    .publisherName("출판사" + i)
                    .publisherEngName("publisher" + i)
                    .build();

            publisherList.add(publisher);
        }
        return publisherList;
    }

    private PublisherDto.Request getRequest() {

        return PublisherDto.Request.builder()
                .publisherName("출판사1")
                .publisherEngName("publisher1")
                .build();
    }

    private PublisherDto.Request getModifyRequest() {


        return PublisherDto.Request.builder()
                .publisherName("출판사2")
                .publisherEngName("publisher2")
                .build();
    }

    private Publisher getPublisher() {

        return Publisher.builder()
                .publisherId(1L)
                .publisherName("출판사1")
                .publisherEngName("publisher1")
                .build();
    }

    private Publisher getModifiedPublisher() {

        return Publisher.builder()
                .publisherId(1L)
                .publisherName("출판사2")
                .publisherEngName("publisher2")
                .build();
    }


    private PublisherDto.Response getResponse() {

        return PublisherDto.Response.builder()
                .publisherId(1L)
                .publisherName("출판사1")
                .publisherEngName("publisher1")
                .build();
    }

    private PublisherDto.Response getModifiedResponse() {

        return PublisherDto.Response.builder()
                .publisherId(1L)
                .publisherName("출판사2")
                .publisherEngName("publisher2")
                .build();
    }

}
