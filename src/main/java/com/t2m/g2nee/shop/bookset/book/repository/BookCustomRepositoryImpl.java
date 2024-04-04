package com.t2m.g2nee.shop.bookset.book.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.t2m.g2nee.shop.bookset.book.domain.Book;
import com.t2m.g2nee.shop.bookset.book.domain.QBook;
import com.t2m.g2nee.shop.bookset.book.dto.BookDto;
import com.t2m.g2nee.shop.bookset.booktag.domain.QBookTag;
import com.t2m.g2nee.shop.bookset.tag.domain.QTag;
import java.util.List;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

public class BookCustomRepositoryImpl extends QuerydslRepositorySupport implements BookCustomRepository {
    public BookCustomRepositoryImpl() {
        super(Book.class);
    }

}
