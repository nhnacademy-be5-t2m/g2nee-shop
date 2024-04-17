package com.t2m.g2nee.shop.memberset.Member.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.t2m.g2nee.shop.memberset.Member.dto.request.SignUpMemberRequestDto;
import com.t2m.g2nee.shop.memberset.Member.dto.request.UsernameRequestDto;
import com.t2m.g2nee.shop.memberset.Member.dto.response.MemberResponse;
import com.t2m.g2nee.shop.memberset.Member.dto.response.MemberResponseToAuth;
import com.t2m.g2nee.shop.memberset.Member.service.Impl.MemberServiceImpl;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;


@WebMvcTest(MemberController.class)
class MemberControllerTest {

    @Autowired
    MockMvc mvc;
    ObjectMapper objectMapper;
    @MockBean
    private MemberServiceImpl memberService;

    SignUpMemberRequestDto signUpMemberRequestDto;
    MemberResponse memberResponse;
    MemberResponseToAuth memberResponseToAuth;
    UsernameRequestDto usernameRequestDto;
    String path = "/shop/member";

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        signUpMemberRequestDto = new SignUpMemberRequestDto(
                "honggildong",
                "gildong123@",
                "홍길동",
                "홍길동",
                "gildong@naver.com",
                "010-1111-1111",
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
        memberResponseToAuth = new MemberResponseToAuth(
                1,
                "gildong",
                "asdf1234!",
                List.of("ROLE_MEMBER")
        );
        usernameRequestDto = new UsernameRequestDto(
                "gildong"
        );
    }

    @Test
    @DisplayName("정상적인 회원가입 성공 Test")
    void memberSignUpComplete() throws Exception {
        when(memberService.signUp(any())).thenReturn(memberResponse);
        mvc.perform(post(path + "/signup")
                        .content(objectMapper.writeValueAsString(signUpMemberRequestDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andDo(print())
                .andExpect(jsonPath("$.userName").value("honggildong"))
                .andExpect(jsonPath("$.name").value("홍길동"))
                .andExpect(jsonPath("$.nickName").value("홍길동"))
                .andExpect(jsonPath("$.grade").value("일반"));

    }

    @Test
    @DisplayName("username으로 회원의 정보를 정상적으로 받아오는 Test")
    void getMemberInfoComplete() throws Exception {
        when(memberService.getMemberInfo(anyString())).thenReturn(memberResponseToAuth);

        mvc.perform(post(path + "/getInfo")
                        .content(objectMapper.writeValueAsString(usernameRequestDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.username").value("gildong"))
                .andExpect(jsonPath("$.password").value("asdf1234!"))
                .andExpect(jsonPath("$.memberId").value(1))
                .andExpect(jsonPath("$.authorities").value("ROLE_MEMBER"));
    }

}