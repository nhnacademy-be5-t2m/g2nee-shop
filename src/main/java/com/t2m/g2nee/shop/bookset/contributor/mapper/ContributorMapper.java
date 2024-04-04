package com.t2m.g2nee.shop.bookset.contributor.mapper;

import com.t2m.g2nee.shop.bookset.contributor.domain.Contributor;
import com.t2m.g2nee.shop.bookset.contributor.dto.ContributorDto;
import com.t2m.g2nee.shop.bookset.role.domain.Role;
import com.t2m.g2nee.shop.bookset.role.dto.RoleDto;
import java.util.List;
import org.mapstruct.Mapper;

/**
 * domain과 response를 변환하는 mapper 클래스
 *
 * @author : 신동민
 * @since : 1.0
 */
@Mapper(componentModel = "spring")
public interface ContributorMapper {

    ContributorDto.Response entityToDto(Contributor contributor);

    List<ContributorDto.Response> entitiesToDtos(List<Contributor> contributorList);
}
