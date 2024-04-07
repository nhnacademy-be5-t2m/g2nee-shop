package com.t2m.g2nee.shop.bookset.book.mapper;

import com.t2m.g2nee.shop.bookset.book.domain.Book;
import com.t2m.g2nee.shop.bookset.book.dto.BookDto;
import org.mapstruct.Mapper;



/**
 * domain과 response를 변환하는 mapper 클래스
 *
 * @author : 신동민
 * @since : 1.0
 */
@Mapper(componentModel = "spring")
public interface BookMapper {

    BookDto.Response entityToDto(Book book);

    Book dtoToEntity(BookDto.Request request);


}
