package com.t2m.g2nee.shop.bookset.book.repository;

import com.t2m.g2nee.shop.bookset.book.domain.Book;
import com.t2m.g2nee.shop.bookset.book.dto.BookDto;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BookCustomRepository {

     List<BookDto.ListResponse> getNewBookList();

     Page<BookDto.ListResponse> getAllBook(Pageable pageable);

     Page<BookDto.ListResponse> getBookListByCategory(Long categoryId, Pageable pageable,String sort);

     BookDto.Response getBookDetail(Long bookId);

     List<BookDto.ListResponse> getBooksByElasticSearchAndCategory(Long categoryId, String keyword,String sort);
}
