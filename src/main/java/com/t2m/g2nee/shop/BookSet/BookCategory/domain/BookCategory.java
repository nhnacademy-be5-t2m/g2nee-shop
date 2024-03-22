package com.t2m.g2nee.shop.BookSet.BookCategory.domain;

import com.t2m.g2nee.shop.BookSet.Book.domain.Book;
import com.t2m.g2nee.shop.BookSet.Categroy.domain.Category;
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
