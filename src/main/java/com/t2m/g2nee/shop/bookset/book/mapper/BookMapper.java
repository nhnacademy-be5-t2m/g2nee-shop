package com.t2m.g2nee.shop.bookset.book.mapper;

import com.t2m.g2nee.shop.bookset.book.domain.Book;
import com.t2m.g2nee.shop.bookset.book.dto.BookDto;
import java.util.List;
import org.mapstruct.Mapper;


/**
 * domain과 response를 변환하는 mapper 인터페이스
 *
 * @author : 신동민
 * @since : 1.0
 */
@Mapper
public interface BookMapper {

    BookDto.Response entityToDto(Book book);

    Book dtoToEntity(BookDto.Request request);

    List<BookDto.ListResponse> entitiesToDto(List<Book> bookList);


}
