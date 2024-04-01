package com.t2m.g2nee.shop.bookset.Role.mapper;

import com.t2m.g2nee.shop.bookset.Role.domain.Role;
import com.t2m.g2nee.shop.bookset.Role.dto.RoleDto;
import java.util.List;
import org.mapstruct.Mapper;

/**
 * domain과 response를 변환하는 mapper 클래스
 *
 * @author : 신동민
 * @since : 1.0
 */
@Mapper(componentModel = "spring")
public interface RoleMapper {

    RoleDto.Response entityToDto(Role Role);

    List<RoleDto.Response> entitiesToDtos(List<Role> roleList);
}
