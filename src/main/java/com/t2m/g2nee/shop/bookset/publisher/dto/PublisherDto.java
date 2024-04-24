package com.t2m.g2nee.shop.bookset.publisher.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import javax.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class PublisherDto {

    private PublisherDto() {
    }

    @Setter
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Request {

        @Pattern(regexp = "^[가-힣0-9]+$", message = "출판사 한글 이름을 입력해주세요.")
        private String publisherName;
        @Pattern(regexp = "^[A-Za-z0-9]+$", message = "출판사 영문 이름을 입력해주세요")
        private String publisherEngName;
    }

    @Setter
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Response {

        private Long publisherId;
        private String publisherName;
        private String publisherEngName;
    }
}
