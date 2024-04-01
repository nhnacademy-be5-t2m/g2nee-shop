package com.t2m.g2nee.shop.bookset.tag.mapper;


import com.t2m.g2nee.shop.bookset.tag.domain.Tag;
import com.t2m.g2nee.shop.bookset.tag.dto.TagDto;
import java.util.List;
import org.mapstruct.Mapper;

/**
 * domain과 response를 변환하는 mapper 클래스
 *
 * @author : 신동민
 * @since : 1.0
 */
@Mapper(componentModel = "spring")
public interface TagMapper {

    TagDto.Response entityToDto(Tag Tag);

    List<TagDto.Response> entitiesToDtos(List<Tag> tagList);
}
