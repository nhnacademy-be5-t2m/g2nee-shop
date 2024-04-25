package com.t2m.g2nee.shop.bookset.book.repository;

import com.t2m.g2nee.shop.bookset.book.dto.BookDto;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;;

public interface BookCustomRepository {

    List<BookDto.ListResponse> getNewBookList();

    Page<BookDto.ListResponse> getAllBook(Pageable pageable);

    Page<BookDto.ListResponse> getBookListByCategory(Long memberId, Long categoryId, Pageable pageable, String sort);

    BookDto.Response getBookDetail(Long memberId, Long bookId);

    Page<BookDto.ListResponse> getBooksByElasticSearchAndCategory(Long memberId, Long categoryId, String keyword,
                                                                  Pageable pageable, String sort);

    List<BookDto.ListResponse> getRecommendBooks(List<Long> categoryIdList, Long bookId);
}
