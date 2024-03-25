package com.t2m.g2nee.shop.bookset.BookCategory.domain;

import com.t2m.g2nee.shop.bookset.Book.domain.Book;
import com.t2m.g2nee.shop.bookset.Category.domain.Category;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "BookCategory")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookCategoryId;

    @ManyToOne
    @JoinColumn(name = "categoryId")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "bookId")
    private Book book;
}
