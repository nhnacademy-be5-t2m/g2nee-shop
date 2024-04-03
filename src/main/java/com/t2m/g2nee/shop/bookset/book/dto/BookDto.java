package com.t2m.g2nee.shop.bookset.book.dto;

import static com.t2m.g2nee.shop.bookset.book.domain.Book.BookStatus;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class BookDto {

    private BookDto() {
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Request {

        @Pattern(regexp = "^[가-힣0-9]+$", message = "출판사 한글 이름을 입력해주세요.")
        private String title;
        @Pattern(regexp = "^[A-Za-z0-9]+$", message = "출판사 영문 이름을 입력해주세요")
        private String engTitle;
        @NotNull(message = "목차를 입력해주세요")
        private String bookIndex;
        @NotNull(message = "설명은 공백 없이 입력해주세요")
        private String description;
        @NotNull
        private LocalDate publishedDate;
        @Pattern(regexp = "\\d+", message = "가격은 숫자로 입력해주세요")
        private int price;
        @Pattern(regexp = "\\d+", message = "가격은 숫자로 입력해주세요")
        private int salePrice;
        @NotNull
        private String isbn;
        @Pattern(regexp = "\\d+", message = "쪽수는 숫자로 입력해주세요")
        private int pages;
        @Pattern(regexp = "\\d+", message = "수량은 숫자로 입력해주세요")
        private int quantity;
        private BookStatus bookStatus;
        private String publisherName;
        private List<String> contributorNameList;
        private List<String> roleNameList;
        private List<String> categoryNameList;
        private List<String> tagNameList;

    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Response {

        private Long bookId;
        private int quantity;
        private String title;
        private String engTitle;
        private String bookIndex;
        private String description;
        private LocalDate publishedDate;
        private int price;
        private int salePrice;
        private String isbn;
        private int viewCount;
        private BookStatus bookStatus;
        private int pages;
        private Map<String, String> contributorRole;
        private List<String> categoryNameList;
        private List<String> tagNameList;
        private String publisherName;
        private String publisherEngName;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class ListResponse {

        private Long bookId;
        private String title;
        private String engTitle;
        private Map<String, String> contributorRole;
        private LocalDate publishedDate;
        private int price;
        private int salePrice;
        private String publisherName;
        private String publisherEngName;

    }
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class statusResponse{
        private BookStatus status;
    }
}
