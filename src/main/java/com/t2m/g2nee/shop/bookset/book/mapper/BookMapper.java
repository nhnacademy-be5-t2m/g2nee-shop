package com.t2m.g2nee.shop.bookset.book.mapper;

import com.t2m.g2nee.shop.bookset.book.domain.Book;
import com.t2m.g2nee.shop.bookset.book.dto.BookDto;
import com.t2m.g2nee.shop.bookset.bookcontributor.domain.BookContributor;
import com.t2m.g2nee.shop.bookset.bookcontributor.dto.BookContributorDto;
import java.util.List;
import java.util.stream.Collectors;
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

    default BookDto.ListResponse entityToListDto(Book book, List<BookContributor> bookContributorList) {

        List<BookContributorDto.Response> bookContributoreResponseList =
                bookContributorList.stream()
                        .map(bc ->
                                BookContributorDto.Response.builder()
                                        .contributorName(bc.getContributor().getContributorName())
                                        .contributorEngName(bc.getContributor().getContributorEngName())
                                        .roleName(bc.getRole().getRoleName())
                                        .build())
                        .collect(Collectors.toList());

        return BookDto.ListResponse.builder()
                .bookId(book.getBookId())
                .title(book.getTitle())
                .engTitle(book.getEngTitle())
                .publishedDate(book.getPublishedDate())
                .price(book.getPrice())
                .salePrice(book.getSalePrice())
                .publisherName(book.getPublisher().getPublisherName())
                .publisherEngName(book.getPublisher().getPublisherEngName())
                .contributorRoleList(bookContributoreResponseList)
                .build();
    }

}
