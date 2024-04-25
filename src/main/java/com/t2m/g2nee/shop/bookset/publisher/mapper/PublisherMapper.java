package com.t2m.g2nee.shop.bookset.publisher.mapper;

import com.t2m.g2nee.shop.bookset.publisher.domain.Publisher;
import com.t2m.g2nee.shop.bookset.publisher.dto.PublisherDto;
import java.util.List;
import org.mapstruct.Mapper;

/**
 * domain과 response를 변환하는 mapper 인터페이스
 *
 * @author : 신동민
 * @since : 1.0
 */
@Mapper
public interface PublisherMapper {

    PublisherDto.Response entityToDto(Publisher publisher);

    List<PublisherDto.Response> entitiesToDtos(List<Publisher> publisherList);
}
