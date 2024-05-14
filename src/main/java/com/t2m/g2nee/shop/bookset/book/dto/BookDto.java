package com.t2m.g2nee.shop.bookset.book.dto;

import static com.t2m.g2nee.shop.bookset.book.domain.Book.BookStatus;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.t2m.g2nee.shop.bookset.bookcontributor.dto.BookContributorDto;
import com.t2m.g2nee.shop.bookset.category.dto.response.CategoryInfoDto;
import com.t2m.g2nee.shop.bookset.tag.dto.TagDto;
import java.time.LocalDate;
import java.util.List;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 책 dto 클래스
 *
 * @author : 신동민
 * @since : 1.0
 */
public class BookDto {

    private BookDto() {
    }

    @Setter
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Request {

        @Pattern(regexp = "^[가-힣 0-9a-zA-Z!@#$%^&*(),.?\":{}|<>]+$", message = "책 이름을 입력해주세요.")
        private String title;
        @Pattern(regexp = "^[A-Za-z 0-9]+$", message = "책 영문 이름을 입력해주세요")
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
        private Long publisherId;
        private List<Long> contributorIdList;
        private List<Long> roleIdList;
        private List<Long> categoryIdList;
        private List<Long> tagIdList;

    }

    @Setter
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Response {

        private Long bookId;
        private List<String> detailImageUrl;
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
        private List<BookContributorDto.Response> contributorRoleList;
        private List<List<CategoryInfoDto>> categoryList;
        private List<TagDto.Response> tagList;
        private String publisherName;
        private String publisherEngName;
        private boolean isLiked;
        private Long reviewCount;
        private Double scoreAverage;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class ListResponse {

        private Long bookId;
        private String thumbnailImageUrl;
        private String title;
        private String engTitle;
        private LocalDate publishedDate;
        private int price;
        private int salePrice;
        private int viewCount;
        private String publisherName;
        private String publisherEngName;
        private BookStatus bookStatus;
        private List<BookContributorDto.Response> contributorRoleList;
        private int quantity;
        private Long bookLikeId;
        private boolean isLiked;
        private Long reviewCount;
        private Double scoreAverage;
    }

    @Setter
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class StatusResponse {
        private BookStatus status;
    }

    @Setter
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class QuantityResponse {
        private int quantity;
    }
}
