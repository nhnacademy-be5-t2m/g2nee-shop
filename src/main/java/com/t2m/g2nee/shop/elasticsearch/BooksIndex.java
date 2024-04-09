package com.t2m.g2nee.shop.elasticsearch;

import com.t2m.g2nee.shop.bookset.book.domain.Book;
import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.stereotype.Service;

@Document(indexName = "books")
@Getter
@Setter
@Service
public class BooksIndex {

    @Id
    private Long bookId;
    private int quantity;
    private String title;
    private String engTitle;
    private String bookIndex;
    private String description;
    private LocalDateTime publishedDate;
    private int price;
    private int salePrice;
    private String isbn;
    private int viewCount;
    private Book.BookStatus bookStatus;
    private int pages;
}
