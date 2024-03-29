package com.t2m.g2nee.shop.review;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.t2m.g2nee.shop.review.controller.ReviewController;
import com.t2m.g2nee.shop.review.dto.request.ReviewChangeRequestDto;
import com.t2m.g2nee.shop.review.dto.request.ReviewCreateRequestDto;
import com.t2m.g2nee.shop.review.dto.response.GetBookReviewResponseDto;
import com.t2m.g2nee.shop.review.service.ReviewService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(ReviewController.class)
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureRestDocs(outputDir = "targets/snippets")
public class ReviewControllerTest {

    @Autowired
    MockMvc mockMvc;
    ObjectMapper objectMapper;

    @MockBean
    ReviewService reviewService;

    String path = "/api/reviews";
    String authPath = "/token/reivews";

    ReviewCreateRequestDto createRequestDto;
    ReviewChangeRequestDto changeRequestDto;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
        createRequestDto = new ReviewCreateRequestDto();
        changeRequestDto = new ReviewChangeRequestDto();


        ReflectionTestUtils.setField(createRequestDto, "customerId", 1L);
        ReflectionTestUtils.setField(createRequestDto, "bookID", 1L);
        ReflectionTestUtils.setField(createRequestDto, "score", 5);
        ReflectionTestUtils.setField(createRequestDto, "content", "1funnyL");
        ReflectionTestUtils.setField(changeRequestDto, "score", 4);
        ReflectionTestUtils.setField(createRequestDto, "content", "1sadL");
    }

    @Test
    @DisplayName("리뷰 조회 성공 테스트")
    void bookReviewSuccess() throws Exception {
        GetBookReviewResponseDto dto =
                new GetBookReviewResponseDto(1L, "nickname", 5, "content");
        
    }
}
