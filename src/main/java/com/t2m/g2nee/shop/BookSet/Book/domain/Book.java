package com.t2m.g2nee.shop.BookSet.Book.domain;

import com.t2m.g2nee.shop.BookSet.Publisher.domain.Publisher;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

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
    private String text;
    private String description;
    private LocalDate publishedDate;
    private int price;
    private int salePrice;
    private String isbn;
    private int viewCount;
    private BookStatus bookStatus;
    private int pages;

    @OneToOne
    @JoinColumn(name = "publisherId")
    private Publisher publisher;

    public enum BookStatus{

        ONSALE, SOLDOUT, OUTOFPRINT, DELETED

    }

}
