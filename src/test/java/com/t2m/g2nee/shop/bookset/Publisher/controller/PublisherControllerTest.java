package com.t2m.g2nee.shop.bookset.Publisher.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.t2m.g2nee.shop.bookset.Publisher.domain.Publisher;
import com.t2m.g2nee.shop.bookset.Publisher.dto.PublisherDto;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@SpringBootTest
@AutoConfigureMockMvc
public class PublisherControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("출판사 생성 컨트롤러 테스트")
    public void testPostPublisher() throws Exception {

        PublisherDto.Request request = getRequest();

        String requestJson = objectMapper.writeValueAsString(request);


        ResultActions resultActions = mockMvc.perform(post("/shop/publisher")
                        .content(requestJson)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print());

        resultActions.andExpect(status().isCreated())
                .andExpect(jsonPath("$.publisherName").value(request.getPublisherName()))
                .andExpect(jsonPath("$.publisherEngName").value(request.getPublisherEngName()));
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
                .publisherEngName("출판사1")
                .publisherEngName("publisher1")
                .build();
    }

    private PublisherDto.Request getModifyRequest() {


        return PublisherDto.Request.builder()
                .publisherEngName("출판사2")
                .publisherEngName("publisher2")
                .build();
    }

    private Publisher getPublisher() {

        return Publisher.builder()
                .publisherId(1L)
                .publisherEngName("출판사1")
                .publisherEngName("publisher1")
                .build();
    }

    private Publisher getModifiedPublisher() {

        return Publisher.builder()
                .publisherId(1L)
                .publisherEngName("출판사2")
                .publisherEngName("publisher2")
                .build();
    }


    private PublisherDto.Response getResponse() {

        return PublisherDto.Response.builder()
                .publisherId(1L)
                .publisherEngName("출판사1")
                .publisherEngName("publisher1")
                .build();
    }

    private PublisherDto.Response getModifiedResponse() {

        return PublisherDto.Response.builder()
                .publisherId(1L)
                .publisherEngName("출판사2")
                .publisherEngName("publisher2")
                .build();
    }
}
