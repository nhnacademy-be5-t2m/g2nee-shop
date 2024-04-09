package com.t2m.g2nee.shop.bookset.role.mapper;

import com.t2m.g2nee.shop.bookset.role.domain.Role;
import com.t2m.g2nee.shop.bookset.role.dto.RoleDto;
import java.util.List;
import org.mapstruct.Mapper;


/**
 * domain과 response를 변환하는 mapper 인터페이스
 *
 * @author : 신동민
 * @since : 1.0
 */
@Mapper
public interface RoleMapper {

    RoleDto.Response entityToDto(Role role);

    List<RoleDto.Response> entitiesToDtos(List<Role> roleList);
}
