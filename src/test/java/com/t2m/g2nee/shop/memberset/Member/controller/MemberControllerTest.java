
package com.t2m.g2nee.shop.memberset.Member.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.t2m.g2nee.shop.memberset.Member.dto.request.SignUpMemberRequestDto;
import com.t2m.g2nee.shop.memberset.Member.dto.response.MemberResponse;
import com.t2m.g2nee.shop.memberset.Member.service.Impl.MemberServiceImpl;
import com.t2m.g2nee.shop.memberset.Member.service.MemberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MemberController.class)
class MemberControllerTest {

    @Autowired
    MockMvc mvc;
    ObjectMapper objectMapper;
    @MockBean
    private MemberServiceImpl memberService;

    @MockBean
    MockHttpServletRequest request;

    SignUpMemberRequestDto signUpMemberRequestDto;
    MemberResponse memberResponse;
    String signUpPath = "/member/signup";

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        signUpMemberRequestDto = new SignUpMemberRequestDto(
                "honggildong",
                "gildong123@",
                "홍길동",
                "홍길동",
                "gildong@naver.com",
                "01011111111",
                "19990203",
                "Male",
                false
        );
        memberResponse = new MemberResponse(
                "honggildong",
                "홍길동",
                "홍길동",
                "일반"
        );
    }

    @Test
    @DisplayName("정상적인 회원가입 성공 Test")
    void memberSignUpComplate() throws Exception {
        when(memberService.signUp(any())).thenReturn(memberResponse);
        mvc.perform(post(signUpPath)
                        .content(objectMapper.writeValueAsString(signUpMemberRequestDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andDo(print())
                .andExpect(jsonPath("$.userName").value("honggildong"))
                .andExpect(jsonPath("$.name").value("홍길동"))
                .andExpect(jsonPath("$.nickName").value("홍길동"))
                .andExpect(jsonPath("$.grade").value("일반"));

    }
}