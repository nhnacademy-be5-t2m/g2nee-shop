package com.t2m.g2nee.shop.bookset.contributor.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import javax.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class ContributorDto {

    private ContributorDto() {
    }

    @Setter
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Request {

        @Pattern(regexp = "^[가-힣0-9]+$", message = "출판사 한글 이름을 입력해주세요.")
        private String contributorName;
        @Pattern(regexp = "^[A-Za-z0-9]+$", message = "출판사 영문 이름을 입력해주세요")
        private String contributorEngName;

    }
    @Setter
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Response {


        private Long contributorId;
        private String contributorName;
        private String contributorEngName;
    }
}
