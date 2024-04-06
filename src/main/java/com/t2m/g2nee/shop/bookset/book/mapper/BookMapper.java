package com.t2m.g2nee.shop.bookset.book.mapper;

import com.t2m.g2nee.shop.bookset.book.domain.Book;
import com.t2m.g2nee.shop.bookset.book.dto.BookDto;
import com.t2m.g2nee.shop.bookset.bookcontributor.domain.BookContributor;
import com.t2m.g2nee.shop.bookset.bookcontributor.dto.BookContributorDto;
import com.t2m.g2nee.shop.bookset.bookcontributor.mapper.BookContributorMapper;
import java.util.List;
import java.util.stream.Collectors;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

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


    @Mapping(source = "book.publisher.publisherName", target = "publisherName")
    @Mapping(source = "book.publisher.publisherEngName", target = "publisherEngName")
    @Mapping(source = "bookContributorList", target = "contributorRoleList", qualifiedByName = "bookContributorEntitiesToDtos")
    BookDto.ListResponse entityToListDto(Book book, List<BookContributor> bookContributorList);

    /**ㄱ
     * 기여자 역할 매핑을 위한 메서드
     * @param bookContributorList
     * @return 기여자역할 응답
     */
    @Named("bookContributorEntitiesToDtos")
    default List<BookContributorDto.Response> entitiesToDtos(List<BookContributor> bookContributorList){

        return bookContributorList.stream()
                .map(bc ->
                        BookContributorDto.Response.builder()
                                .contributorName(bc.getContributor().getContributorName())
                                .contributorEngName(bc.getContributor().getContributorEngName())
                                .roleName(bc.getRole().getRoleName())
                                .build())
                .collect(Collectors.toList());
    }
}
