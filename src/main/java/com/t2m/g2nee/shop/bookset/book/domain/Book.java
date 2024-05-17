package com.t2m.g2nee.shop.bookset.book.domain;

import com.t2m.g2nee.shop.bookset.publisher.domain.Publisher;
import java.time.LocalDate;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Books")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    @Enumerated(EnumType.STRING)
    private BookStatus bookStatus;
    private int pages;

    @ManyToOne
    @JoinColumn(name = "publisherId")
    private Publisher publisher;


    @Getter
    public enum BookStatus {


        ONSALE("정상판매"), SOLDOUT("매진"), OUTOFPRINT("절판"), DELETED("삭제");

        private final String status;

        BookStatus(String status) {
            this.status = status;
        }
    }

    @Getter
    public enum SearchCondition {

        TITLE("제목"),INTEGRATION("통합검색"), PUBLISHER("출판사"), CONTRIBUTOR("참여자"), TAG("태그");

        private final String condition;

        SearchCondition(String condition) {
            this.condition = condition;
        }
    }

}
