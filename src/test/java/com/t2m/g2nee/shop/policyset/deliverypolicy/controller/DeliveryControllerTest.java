package com.t2m.g2nee.shop.policyset.deliverypolicy.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.t2m.g2nee.shop.pageUtils.PageResponse;
import com.t2m.g2nee.shop.policyset.deliverypolicy.dto.request.DeliveryPolicySaveDto;
import com.t2m.g2nee.shop.policyset.deliverypolicy.dto.response.DeliveryPolicyInfoDto;
import com.t2m.g2nee.shop.policyset.deliverypolicy.service.DeliveryPolicyService;
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
class DeliveryControllerTest {

    ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private DeliveryPolicyService service;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    @DisplayName("배송비 정책 저장")
    void testCreateDeliveryPolicy() throws Exception {
        DeliveryPolicySaveDto request = new DeliveryPolicySaveDto(2500, 10000);
        DeliveryPolicyInfoDto deliveryPolicy = new DeliveryPolicyInfoDto(1L, 2500, 10000, true,
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));

        when(service.saveDeliveryPolicy(any(DeliveryPolicySaveDto.class))).thenReturn(deliveryPolicy);

        mockMvc.perform(post("/api/v1/shop/deliveryPolicies")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.deliveryPolicyId", equalTo(1)))
                .andExpect(jsonPath("$.deliveryFee", equalTo(deliveryPolicy.getDeliveryFee())))
                .andExpect(jsonPath("$.freeDeliveryStandard", equalTo(deliveryPolicy.getFreeDeliveryStandard())))
                .andExpect(jsonPath("$.isActivated", equalTo(deliveryPolicy.getIsActivated())))
                .andExpect(jsonPath("$.changedDate", equalTo(deliveryPolicy.getChangedDate())));
    }

    @Test
    @DisplayName("현재 시행중인 배송비 정책 테스트")
    void testGetDeliveryPolicy() throws Exception {
        DeliveryPolicyInfoDto deliveryPolicy = new DeliveryPolicyInfoDto(1L, 2500, 10000, true,
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));

        when(service.getDeliveryPolicy()).thenReturn(deliveryPolicy);

        mockMvc.perform(get("/api/v1/shop/deliveryPolicies/recentPolicy", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.deliveryPolicyId", equalTo(1)))
                .andExpect(jsonPath("$.deliveryFee", equalTo(deliveryPolicy.getDeliveryFee())))
                .andExpect(jsonPath("$.freeDeliveryStandard", equalTo(deliveryPolicy.getFreeDeliveryStandard())))
                .andExpect(jsonPath("$.isActivated", equalTo(deliveryPolicy.getIsActivated())))
                .andExpect(jsonPath("$.changedDate", equalTo(deliveryPolicy.getChangedDate())));
    }

    @Test
    @DisplayName("배송비 정책 목록 얻기 테스트")
    void testGetAllDeliveryPolicies() throws Exception {
        DeliveryPolicyInfoDto deliveryPolicy1 = new DeliveryPolicyInfoDto(1L, 2500, 10000, false,
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        DeliveryPolicyInfoDto deliveryPolicy2 = new DeliveryPolicyInfoDto(2L, 3000, 50000, true,
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));

        List<DeliveryPolicyInfoDto> deliveryPolicies = List.of(deliveryPolicy2, deliveryPolicy1);

        PageResponse<DeliveryPolicyInfoDto> deliveryPolicyPage =
                PageResponse.<DeliveryPolicyInfoDto>builder()
                        .data(deliveryPolicies)
                        .currentPage(1)
                        .totalPage(1)
                        .build();

        when(service.getAllDeliveryPolicy(anyInt())).thenReturn(deliveryPolicyPage);

        mockMvc.perform(get("/api/v1/shop/deliveryPolicies?page=1", 1L))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data", hasSize(2)))
                .andExpect(jsonPath("$.data[0].deliveryPolicyId", equalTo(2)))
                .andExpect(jsonPath("$.data[0].deliveryFee", equalTo(deliveryPolicy2.getDeliveryFee())))
                .andExpect(
                        jsonPath("$.data[0].freeDeliveryStandard", equalTo(deliveryPolicy2.getFreeDeliveryStandard())))
                .andExpect(jsonPath("$.data[0].isActivated", equalTo(deliveryPolicy2.getIsActivated())))
                .andExpect(jsonPath("$.data[0].changedDate", equalTo(deliveryPolicy2.getChangedDate())))
                .andExpect(jsonPath("$.data[1].deliveryPolicyId", equalTo(1)))
                .andExpect(jsonPath("$.data[1].deliveryFee", equalTo(deliveryPolicy1.getDeliveryFee())))
                .andExpect(
                        jsonPath("$.data[1].freeDeliveryStandard", equalTo(deliveryPolicy1.getFreeDeliveryStandard())))
                .andExpect(jsonPath("$.data[1].isActivated", equalTo(deliveryPolicy1.getIsActivated())))
                .andExpect(jsonPath("$.data[1].changedDate", equalTo(deliveryPolicy1.getChangedDate())));
    }
}
