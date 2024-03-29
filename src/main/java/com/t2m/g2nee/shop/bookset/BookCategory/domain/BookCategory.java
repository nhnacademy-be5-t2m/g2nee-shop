package com.t2m.g2nee.shop.bookset.BookCategory.domain;

import com.t2m.g2nee.shop.bookset.Book.domain.Book;
import com.t2m.g2nee.shop.bookset.Category.domain.Category;
import javax.persistence.Entity;
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
