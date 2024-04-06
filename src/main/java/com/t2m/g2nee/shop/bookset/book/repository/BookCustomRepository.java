package com.t2m.g2nee.shop.bookset.book.repository;

import com.t2m.g2nee.shop.bookset.book.dto.BookDto;
import java.util.List;

public interface BookCustomRepository {

     List<BookDto.ListResponse> getNewBookList();
}
