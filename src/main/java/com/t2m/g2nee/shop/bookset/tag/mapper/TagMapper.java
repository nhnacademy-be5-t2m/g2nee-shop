package com.t2m.g2nee.shop.bookset.tag.mapper;


import com.t2m.g2nee.shop.bookset.tag.domain.Tag;
import com.t2m.g2nee.shop.bookset.tag.dto.TagDto;
import java.util.List;
import org.mapstruct.Mapper;

/**
 * domain과 response를 변환하는 mapper 인터페이스
 *
 * @author : 신동민
 * @since : 1.0
 */
@Mapper
public interface TagMapper {

    TagDto.Response entityToDto(Tag tag);

    List<TagDto.Response> entitiesToDtos(List<Tag> tagList);
}
