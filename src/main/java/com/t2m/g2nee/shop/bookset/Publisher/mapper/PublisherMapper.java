package com.t2m.g2nee.shop.bookset.Publisher.mapper;

import com.t2m.g2nee.shop.bookset.Publisher.domain.Publisher;
import com.t2m.g2nee.shop.bookset.Publisher.dto.PublisherDto;
import java.util.List;
import org.mapstruct.Mapper;

/**
 * domain과 response를 변환하는 mapper 클래스
 *
 * @author : 신동민
 * @since : 1.0
 */
@Mapper(componentModel = "spring")
public interface PublisherMapper {

    PublisherDto.Response entityToDto(Publisher publisher);

    List<PublisherDto.Response> entitiesToDtos(List<Publisher> publisherList);
}
