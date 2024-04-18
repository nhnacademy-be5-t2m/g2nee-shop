package com.t2m.g2nee.shop.policyset.pointPolicy.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.t2m.g2nee.shop.pageUtils.PageResponse;
import com.t2m.g2nee.shop.policyset.pointPolicy.dto.request.PointPolicySaveDto;
import com.t2m.g2nee.shop.policyset.pointPolicy.dto.response.PointPolicyInfoDto;
import com.t2m.g2nee.shop.policyset.pointPolicy.service.PointPolicyService;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class PointControllerTest {

    ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private PointPolicyService service;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    @DisplayName("포인트 정책 저장")
    void testCreatePackage() throws Exception {
        PointPolicySaveDto request = new PointPolicySaveDto("테스트 정책1", "PERCENT", BigDecimal.valueOf(0.25));
        PointPolicyInfoDto pointPolicy =
                new PointPolicyInfoDto(1L, "테스트 정책1", "PERCENT", BigDecimal.valueOf(0.25).toString(), true,
                        LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));

        when(service.savePointPolicy(any(PointPolicySaveDto.class))).thenReturn(pointPolicy);

        mockMvc.perform(post("/shop/pointPolicy")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.pointPolicyId", equalTo(1)))
                .andExpect(jsonPath("$.policyName", equalTo(pointPolicy.getPolicyName())))
                .andExpect(jsonPath("$.policyType", equalTo(pointPolicy.getPolicyType())))
                .andExpect(jsonPath("$.amount", equalTo(pointPolicy.getAmount())))
                .andExpect(jsonPath("$.changedDate", equalTo(pointPolicy.getChangedDate())))
                .andExpect(jsonPath("$.isActivated", equalTo(pointPolicy.getIsActivated())));
    }

    @Test
    @DisplayName("포인트 정책 수정 테스트")
    void testUpdatePackage() throws Exception {
        PointPolicySaveDto request = new PointPolicySaveDto("테스트 정책1", "PERCENT", BigDecimal.valueOf(0.25));
        PointPolicyInfoDto pointPolicy =
                new PointPolicyInfoDto(1L, "테스트 정책1", "PERCENT", BigDecimal.valueOf(0.25).toString(), true,
                        LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));

        when(service.updatePointPolicy(anyLong(), any(PointPolicySaveDto.class))).thenReturn(pointPolicy);

        mockMvc.perform(put("/shop/pointPolicy/{pointPolicyId}", 1L)
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.pointPolicyId", equalTo(1)))
                .andExpect(jsonPath("$.policyName", equalTo(pointPolicy.getPolicyName())))
                .andExpect(jsonPath("$.policyType", equalTo(pointPolicy.getPolicyType())))
                .andExpect(jsonPath("$.amount", equalTo(pointPolicy.getAmount())))
                .andExpect(jsonPath("$.changedDate", equalTo(pointPolicy.getChangedDate())))
                .andExpect(jsonPath("$.isActivated", equalTo(pointPolicy.getIsActivated())));

    }

    @Test
    @DisplayName("포인트 정책 삭제 테스트")
    void testDeletePackage() throws Exception {
        when(service.softDeletePointPolicy(anyLong())).thenReturn(false);

        mockMvc.perform(patch("/shop/pointPolicy/{pointPolicyId}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", equalTo(false)));

    }

    @Test
    @DisplayName("포인트 정책 1개 얻기 테스트")
    void testGetPackage() throws Exception {
        PointPolicyInfoDto pointPolicy =
                new PointPolicyInfoDto(1L, "테스트 정책1", "PERCENT", BigDecimal.valueOf(0.25).toString(), true,
                        LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));

        when(service.getPointPolicy(anyLong())).thenReturn(pointPolicy);

        mockMvc.perform(get("/shop/pointPolicy/{pointPolicyId}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.pointPolicyId", equalTo(1)))
                .andExpect(jsonPath("$.policyName", equalTo(pointPolicy.getPolicyName())))
                .andExpect(jsonPath("$.policyType", equalTo(pointPolicy.getPolicyType())))
                .andExpect(jsonPath("$.amount".toString(), equalTo(pointPolicy.getAmount().toString())))
                .andExpect(jsonPath("$.changedDate", equalTo(pointPolicy.getChangedDate())))
                .andExpect(jsonPath("$.isActivated", equalTo(pointPolicy.getIsActivated())));
    }

    @Test
    @DisplayName("포인트 정책 목록 얻기 테스트")
    void testGetAllPackages() throws Exception {
        PointPolicyInfoDto pointPolicy1 =
                new PointPolicyInfoDto(1L, "테스트 정책1", "PERCENT", BigDecimal.valueOf(0.25).toString(), true,
                        LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        PointPolicyInfoDto pointPolicy2 =
                new PointPolicyInfoDto(2L, "테스트 정책1", "AMOUNT", BigDecimal.valueOf(1000).toString(), false,
                        LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        PointPolicyInfoDto pointPolicy3 =
                new PointPolicyInfoDto(3L, "테스트 정책1", "PERCENT", BigDecimal.valueOf(0.1).toString(), true,
                        LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));

        List<PointPolicyInfoDto> pointPolicies = List.of(pointPolicy1, pointPolicy3, pointPolicy2);

        PageResponse<PointPolicyInfoDto> pointPolicyPage =
                PageResponse.<PointPolicyInfoDto>builder()
                        .data(pointPolicies)
                        .currentPage(1)
                        .totalPage(1)
                        .build();

        when(service.getAllPointPolicy(anyInt())).thenReturn(pointPolicyPage);

        mockMvc.perform(get("/shop/pointPolicy?page=1", 1L))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data", hasSize(3)))
                .andExpect(jsonPath("$.data[0].pointPolicyId", equalTo(1)))
                .andExpect(jsonPath("$.data[0].policyName", equalTo(pointPolicy1.getPolicyName())))
                .andExpect(jsonPath("$.data[0].policyType", equalTo(pointPolicy1.getPolicyType())))
                .andExpect(jsonPath("$.data[0].amount", equalTo(pointPolicy1.getAmount())))
                .andExpect(jsonPath("$.data[0].changedDate", equalTo(pointPolicy1.getChangedDate())))
                .andExpect(jsonPath("$.data[0].isActivated", equalTo(pointPolicy1.getIsActivated())))
                .andExpect(jsonPath("$.data[1].pointPolicyId", equalTo(3)))
                .andExpect(jsonPath("$.data[1].policyName", equalTo(pointPolicy3.getPolicyName())))
                .andExpect(jsonPath("$.data[1].policyType", equalTo(pointPolicy3.getPolicyType())))
                .andExpect(jsonPath("$.data[1].amount", equalTo(pointPolicy3.getAmount())))
                .andExpect(jsonPath("$.data[1].changedDate", equalTo(pointPolicy3.getChangedDate())))
                .andExpect(jsonPath("$.data[1].isActivated", equalTo(pointPolicy3.getIsActivated())))
                .andExpect(jsonPath("$.data[2].pointPolicyId", equalTo(2)))
                .andExpect(jsonPath("$.data[2].policyName", equalTo(pointPolicy2.getPolicyName())))
                .andExpect(jsonPath("$.data[2].policyType", equalTo(pointPolicy2.getPolicyType())))
                .andExpect(jsonPath("$.data[2].amount", equalTo(pointPolicy2.getAmount())))
                .andExpect(jsonPath("$.data[2].changedDate", equalTo(pointPolicy2.getChangedDate())))
                .andExpect(jsonPath("$.data[2].isActivated", equalTo(pointPolicy2.getIsActivated())));
    }
}
