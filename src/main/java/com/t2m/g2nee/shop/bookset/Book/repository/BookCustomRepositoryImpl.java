package com.t2m.g2nee.shop.bookset.Book.repository;

import com.t2m.g2nee.shop.bookset.Book.domain.Book;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

public class BookCustomRepositoryImpl extends QuerydslRepositorySupport implements BookCustomRepository {
    public BookCustomRepositoryImpl() {
        super(Book.class);
    }
}
