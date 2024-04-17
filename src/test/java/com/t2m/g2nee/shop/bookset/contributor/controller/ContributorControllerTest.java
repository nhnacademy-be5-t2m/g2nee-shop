package com.t2m.g2nee.shop.bookset.contributor.controller;

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
import com.t2m.g2nee.shop.bookset.contributor.domain.Contributor;
import com.t2m.g2nee.shop.bookset.contributor.dto.ContributorDto;
import com.t2m.g2nee.shop.bookset.contributor.mapper.ContributorMapper;
import com.t2m.g2nee.shop.bookset.contributor.service.ContributorService;
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

@WebMvcTest(ContributorController.class)
class ContributorControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ContributorService contributorService;

    @MockBean
    private ContributorMapper mapper;

    private final String url = "/shop/contributors/";

    @Test
    @DisplayName("한글 유효성 검사 실패 후 응답값 테스트")
    void testKorValidation() throws Exception {

        ContributorDto.Request request = ContributorDto.Request.builder()
                .contributorName("eng")
                .contributorEngName("eng")
                .build();

        String requestJson = objectMapper.writeValueAsString(request);

        when(contributorService.registerContributor(any(Contributor.class))).thenReturn(
                ContributorDto.Response.class.newInstance());

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

        ContributorDto.Request request = ContributorDto.Request.builder()
                .contributorName("한글")
                .contributorEngName("한글")
                .build();

        String requestJson = objectMapper.writeValueAsString(request);

        when(contributorService.registerContributor(any(Contributor.class))).thenReturn(
                ContributorDto.Response.class.newInstance());

        ResultActions resultActions = mockMvc.perform(post(url)
                .content(requestJson)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        resultActions.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(400));
    }


    @Test
    @DisplayName("기여자 생성 컨트롤러 테스트")
    void testPostContributor() throws Exception {

        // given
        ContributorDto.Request request = getRequest();
        ContributorDto.Response response = getResponse();

        String requestJson = objectMapper.writeValueAsString(request);

        when(contributorService.registerContributor(any(Contributor.class))).thenReturn(response);

        //when
        ResultActions resultActions = mockMvc.perform(post(url)
                .content(requestJson)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        //then
        resultActions.andExpect(status().isCreated())
                .andExpect(jsonPath("$.contributorName").value(request.getContributorName()))
                .andExpect(jsonPath("$.contributorEngName").value(request.getContributorEngName()));
    }

    @Test
    @DisplayName("기여자 수정 컨트롤러 테스트")
    void testUpdateContributor() throws Exception {

        //given
        ContributorDto.Request request = getModifyRequest();
        ;
        ContributorDto.Response modifyresponse = getModifiedResponse();
        Contributor contributor = getContributor();

        String requestJson = objectMapper.writeValueAsString(request);

        when(contributorService.updateContributor(any(Contributor.class))).thenReturn(modifyresponse);

        //when  //then
        ResultActions resultActions = mockMvc.perform(patch(url + contributor.getContributorId())
                .content(requestJson)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));


        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.contributorName").value(request.getContributorName()))
                .andExpect(jsonPath("$.contributorEngName").value(request.getContributorEngName()));
    }

    @Test
    @DisplayName("기여자 리스트 조회 컨트롤러 테스트")
    void testGetContributorList() throws Exception {

        //given
        List<Contributor> publisherList = getList();
        List<ContributorDto.Response> responses = getResponseList();
        int page = 1;
        int size = 10;
        int totalPage = (int) Math.ceil((double) publisherList.size() / size);
        PageResponse<ContributorDto.Response> pageResponse = PageResponse.<ContributorDto.Response>builder()
                .data(responses)
                .totalPage(totalPage)
                .currentPage(page)
                .build();

        when(contributorService.getContributorList(page)).thenReturn(pageResponse);
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
                            jsonPath("$.data[" + i + "].contributorName").value(responses.get(i).getContributorName()))
                    .andExpect(jsonPath("$.data[" + i + "].contributorEngName").value(
                            responses.get(i).getContributorEngName()));
        }

    }


    @Test
    @DisplayName("기여자 삭제 컨트롤러 테스트")
    void deleteContributorTest() throws Exception {

        //given
        Contributor contributor = getContributor();


        doNothing().when(contributorService).deleteContributor(contributor.getContributorId());

        //when
        mockMvc.perform(delete(url + contributor.getContributorId()))
                .andExpect(status().isNoContent());

    }

    private List<Contributor> getList() {

        List<Contributor> contributorList = new ArrayList<>();
        for (int i = 1; i < 21; i++) {
            Contributor contributor = Contributor.builder()
                    .contributorId((long) i)
                    .contributorName("기여자" + i)
                    .contributorEngName("contributor" + i)
                    .build();

            contributorList.add(contributor);
        }
        return contributorList;
    }

    private List<ContributorDto.Response> getResponseList() {

        List<ContributorDto.Response> contributorList = new ArrayList<>();
        for (int i = 1; i < 11; i++) {
            ContributorDto.Response publisher = ContributorDto.Response.builder()
                    .contributorId((long) i)
                    .contributorName("기여자" + i)
                    .contributorEngName("contributor" + i)
                    .build();

            contributorList.add(publisher);
        }
        return contributorList;
    }

    private ContributorDto.Request getRequest() {

        return ContributorDto.Request.builder()
                .contributorName("기여자1")
                .contributorEngName("contributor1")
                .build();
    }

    private ContributorDto.Request getModifyRequest() {


        return ContributorDto.Request.builder()
                .contributorName("기여자2")
                .contributorEngName("contributor2")
                .build();
    }

    private Contributor getContributor() {

        return Contributor.builder()
                .contributorId(1L)
                .contributorName("기여자1")
                .contributorEngName("contributor1")
                .build();
    }

    private Contributor getModifiedContributor() {

        return Contributor.builder()
                .contributorId(1L)
                .contributorName("기여자2")
                .contributorEngName("contributor2")
                .build();
    }


    private ContributorDto.Response getResponse() {

        return ContributorDto.Response.builder()
                .contributorId(1L)
                .contributorName("기여자1")
                .contributorEngName("contributor1")
                .build();
    }

    private ContributorDto.Response getModifiedResponse() {

        return ContributorDto.Response.builder()
                .contributorId(1L)
                .contributorName("기여자2")
                .contributorEngName("contributor2")
                .build();
    }
}
