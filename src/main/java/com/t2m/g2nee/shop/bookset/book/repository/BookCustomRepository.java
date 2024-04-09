package com.t2m.g2nee.shop.bookset.book.repository;

import com.t2m.g2nee.shop.bookset.book.dto.BookDto;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BookCustomRepository {

     List<BookDto.ListResponse> getNewBookList();

     Page<BookDto.ListResponse> getBookListByCategory(Long categoryId, Pageable pageable);

     BookDto.Response getBookDetail(Long bookId);

     List<BookDto.ListResponse> getBooksByElasticSearchAndCategory(int page, Long categoryId, String keyword);
}
