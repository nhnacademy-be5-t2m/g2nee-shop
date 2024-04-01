package com.t2m.g2nee.shop.bookset.book.repository;

import com.t2m.g2nee.shop.bookset.book.domain.Book;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

public class BookCustomRepositoryImpl extends QuerydslRepositorySupport implements BookCustomRepository {
    public BookCustomRepositoryImpl() {
        super(Book.class);
    }
}
