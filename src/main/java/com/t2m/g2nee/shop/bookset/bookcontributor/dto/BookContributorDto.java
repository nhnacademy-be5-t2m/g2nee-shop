package com.t2m.g2nee.shop.bookset.bookcontributor.dto;

import com.t2m.g2nee.shop.bookset.book.domain.Book;
import com.t2m.g2nee.shop.bookset.contributor.domain.Contributor;
import com.t2m.g2nee.shop.bookset.role.domain.Role;
import com.t2m.g2nee.shop.bookset.tag.domain.Tag;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * 책 기여자 dto 입니다.
 *
 * @author : 신동민
 * @since : 1.0
 */
public class BookContributorDto {

    private BookContributorDto() {
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Request {

        private Contributor contributor;
        private Book book;
        private Role role;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Response {

        private Long contributorId;
        private String contributorName;
        private String contributorEngName;
        private String roleName;
    }
}
