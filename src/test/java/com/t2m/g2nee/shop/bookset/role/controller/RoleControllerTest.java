package com.t2m.g2nee.shop.bookset.role.controller;


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
import com.t2m.g2nee.shop.bookset.role.domain.Role;
import com.t2m.g2nee.shop.bookset.role.dto.RoleDto;
import com.t2m.g2nee.shop.bookset.role.mapper.RoleMapper;
import com.t2m.g2nee.shop.bookset.role.service.RoleService;
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


@WebMvcTest(RoleController.class)
class RoleControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private RoleService roleService;

    @MockBean
    private RoleMapper mapper;

    private final String url = "/api/v1/shop/roles/";

    @Test
    @DisplayName("유효성 검사 실패 후 응답값 테스트")
    void testKorValidation() throws Exception {

        RoleDto.Request request = RoleDto.Request.builder()
                .roleName(" ")
                .build();

        String requestJson = objectMapper.writeValueAsString(request);

        when(roleService.registerRole(any(Role.class))).thenReturn(
                RoleDto.Response.class.newInstance());

        ResultActions resultActions = mockMvc.perform(post(url)
                .content(requestJson)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        resultActions.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(400));
    }

    @Test
    @DisplayName("역할 생성 컨트롤러 테스트")
    void testPostRole() throws Exception {

        // given
        RoleDto.Request request = getRequest();
        RoleDto.Response response = getResponse();

        String requestJson = objectMapper.writeValueAsString(request);

        when(roleService.registerRole(any(Role.class))).thenReturn(response);

        //when
        ResultActions resultActions = mockMvc.perform(post(url)
                .content(requestJson)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        //then
        resultActions.andExpect(status().isCreated())
                .andExpect(jsonPath("$.roleName").value(request.getRoleName()));
    }

    @Test
    @DisplayName("역할 수정 컨트롤러 테스트")
    void testUpdateRole() throws Exception {

        //given
        RoleDto.Request request = getModifyRequest();
        ;
        RoleDto.Response modifyresponse = getModifiedResponse();
        Role role = getRole();

        String requestJson = objectMapper.writeValueAsString(request);

        when(roleService.updateRole(any(Role.class))).thenReturn(modifyresponse);

        //when  //then
        ResultActions resultActions = mockMvc.perform(patch(url + role.getRoleId())
                .content(requestJson)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));


        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.roleName").value(request.getRoleName()));
    }

    @Test
    @DisplayName("역할 리스트 조회 컨트롤러 테스트")
    void testGetRoleList() throws Exception {

        //given
        List<Role> roleList = getList();
        List<RoleDto.Response> responses = getResponseList();
        int page = 1;
        int size = 10;
        int totalPage = (int) Math.ceil((double) roleList.size() / size);
        PageResponse<RoleDto.Response> pageResponse = PageResponse.<RoleDto.Response>builder()
                .data(responses)
                .totalPage(totalPage)
                .currentPage(page)
                .build();

        when(roleService.getRoleList(page)).thenReturn(pageResponse);
        when(mapper.entitiesToDtos(roleList)).thenReturn(responses);

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
                    jsonPath("$.data[" + i + "].roleName").value(responses.get(i).getRoleName()));
        }

    }


    @Test
    @DisplayName("역할 삭제 컨트롤러 테스트")
    void deleteRoleTest() throws Exception {

        //given
        Role role = getRole();

        doNothing().when(roleService).deleteRole(role.getRoleId());

        //when
        mockMvc.perform(delete(url + role.getRoleId()))
                .andExpect(status().isNoContent());

    }

    private List<Role> getList() {

        List<Role> roleList = new ArrayList<>();
        for (int i = 1; i < 21; i++) {
            Role role = Role.builder()
                    .roleId((long) i)
                    .roleName("역할" + i)
                    .build();

            roleList.add(role);
        }
        return roleList;
    }

    private List<RoleDto.Response> getResponseList() {

        List<RoleDto.Response> roleList = new ArrayList<>();
        for (int i = 1; i < 11; i++) {
            RoleDto.Response tag = RoleDto.Response.builder()
                    .roleId((long) i)
                    .roleName("역할" + i)

                    .build();

            roleList.add(tag);
        }
        return roleList;
    }

    private RoleDto.Request getRequest() {

        return RoleDto.Request.builder()
                .roleName("역할1")
                .build();
    }

    private RoleDto.Request getModifyRequest() {


        return RoleDto.Request.builder()
                .roleName("역할2")
                .build();
    }

    private Role getRole() {

        return Role.builder()
                .roleId(1L)
                .roleName("역할1")
                .build();
    }

    private Role getModifiedRole() {

        return Role.builder()
                .roleId(1L)
                .roleName("역할2")
                .build();
    }


    private RoleDto.Response getResponse() {

        return RoleDto.Response.builder()
                .roleId(1L)
                .roleName("역할1")
                .build();
    }

    private RoleDto.Response getModifiedResponse() {

        return RoleDto.Response.builder()
                .roleId(1L)
                .roleName("역할2")
                .build();
    }
}
