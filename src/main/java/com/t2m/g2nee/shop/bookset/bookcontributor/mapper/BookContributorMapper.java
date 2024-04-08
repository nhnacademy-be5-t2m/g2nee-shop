package com.t2m.g2nee.shop.bookset.bookcontributor.mapper;

import com.t2m.g2nee.shop.bookset.bookcontributor.domain.BookContributor;
import com.t2m.g2nee.shop.bookset.bookcontributor.dto.BookContributorDto;
import java.util.List;
import java.util.stream.Collectors;
import org.mapstruct.Mapper;

/**
 * domain과 response를 변환하는 mapper 인터페이스
 *
 * @author : 신동민
 * @since : 1.0
 */

@Mapper
public interface BookContributorMapper {

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
