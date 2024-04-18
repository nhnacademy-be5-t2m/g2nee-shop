package com.t2m.g2nee.shop.orderset.packageType.controller;

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
import com.t2m.g2nee.shop.orderset.packageType.dto.request.PackageSaveDto;
import com.t2m.g2nee.shop.orderset.packageType.dto.response.PackageInfoDto;
import com.t2m.g2nee.shop.orderset.packageType.service.PackageService;
import com.t2m.g2nee.shop.pageUtils.PageResponse;
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
class PackageControllerTest {

    ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private PackageService service;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    @DisplayName("포장지 저장 테스트")
    void testCreatePackage() throws Exception {
        PackageSaveDto request = new PackageSaveDto("포장지1", 1001, true);
        PackageInfoDto packages = new PackageInfoDto(1L, "포장지1", 1001, true);

        when(service.savePackage(any(PackageSaveDto.class))).thenReturn(packages);

        mockMvc.perform(post("/shop/package")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.packageId", equalTo(1)))
                .andExpect(jsonPath("$.name", equalTo(packages.getName())))
                .andExpect(jsonPath("$.price", equalTo(packages.getPrice())))
                .andExpect(jsonPath("$.isActivated", equalTo(packages.getIsActivated())));
    }

    @Test
    @DisplayName("포장지 수정 테스트")
    void testUpdatePackage() throws Exception {
        PackageSaveDto request = new PackageSaveDto("포장지1", 1001, true);
        PackageInfoDto packages = new PackageInfoDto(1L, "포장지1", 1001, true);

        when(service.updatePackage(anyLong(), any(PackageSaveDto.class))).thenReturn(packages);

        mockMvc.perform(put("/shop/package/{packageId}", 1L)
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.packageId", equalTo(1)))
                .andExpect(jsonPath("$.name", equalTo(packages.getName())))
                .andExpect(jsonPath("$.price", equalTo(packages.getPrice())))
                .andExpect(jsonPath("$.isActivated", equalTo(packages.getIsActivated())));

    }

    @Test
    @DisplayName("포장지 삭제 테스트")
    void testDeletePackage() throws Exception {
        when(service.softDeletePackage(anyLong())).thenReturn(false);

        mockMvc.perform(patch("/shop/package/delete/{packageId}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", equalTo(false)));

    }

    @Test
    @DisplayName("포장지 활성화 테스트")
    void testActivePackage() throws Exception {
        when(service.activatedPackage(anyLong())).thenReturn(true);

        mockMvc.perform(patch("/shop/package/activate/{packageId}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", equalTo(true)));
    }

    @Test
    @DisplayName("포장지 1개 얻기 테스트")
    void testGetPackage() throws Exception {
        PackageInfoDto packages = new PackageInfoDto(1L, "포장지1", 1001, true);


        when(service.getPackage(anyLong())).thenReturn(packages);

        mockMvc.perform(get("/shop/package/{packageId}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.packageId", equalTo(1)))
                .andExpect(jsonPath("$.name", equalTo(packages.getName())))
                .andExpect(jsonPath("$.price", equalTo(packages.getPrice())))
                .andExpect(jsonPath("$.isActivated", equalTo(packages.getIsActivated())));
    }

    @Test
    @DisplayName("포장지 목록 얻기 테스트")
    void testGetAllPackages() throws Exception {
        PackageInfoDto package1 = new PackageInfoDto(1L, "포장지1", 1001, true);
        PackageInfoDto package2 = new PackageInfoDto(2L, "포장지2", 1002, false);
        PackageInfoDto package3 = new PackageInfoDto(3L, "포장지3", 1003, true);

        List<PackageInfoDto> packages = List.of(package1, package2, package3);

        PageResponse<PackageInfoDto> packagePage =
                PageResponse.<PackageInfoDto>builder()
                        .data(packages)
                        .currentPage(1)
                        .totalPage(1)
                        .build();

        when(service.getAllPackages(anyInt())).thenReturn(packagePage);

        mockMvc.perform(get("/shop/package?page=1", 1))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data", hasSize(3)))
                .andExpect(jsonPath("$.data[0].packageId", equalTo(1)))
                .andExpect(jsonPath("$.data[0].name", equalTo(package1.getName())))
                .andExpect(jsonPath("$.data[0].price", equalTo(package1.getPrice())))
                .andExpect(jsonPath("$.data[0].isActivated", equalTo(package1.getIsActivated())))
                .andExpect(jsonPath("$.data[1].packageId", equalTo(2)))
                .andExpect(jsonPath("$.data[1].name", equalTo(package2.getName())))
                .andExpect(jsonPath("$.data[1].price", equalTo(package2.getPrice())))
                .andExpect(jsonPath("$.data[1].isActivated", equalTo(package2.getIsActivated())))
                .andExpect(jsonPath("$.data[2].packageId", equalTo(3)))
                .andExpect(jsonPath("$.data[2].name", equalTo(package3.getName())))
                .andExpect(jsonPath("$.data[2].price", equalTo(package3.getPrice())))
                .andExpect(jsonPath("$.data[2].isActivated", equalTo(package3.getIsActivated())));
    }
}
