package com.t2m.g2nee.shop.bookset.Role.service;

import com.t2m.g2nee.shop.bookset.Role.domain.Role;
import com.t2m.g2nee.shop.bookset.Role.dto.RoleDto;
import com.t2m.g2nee.shop.bookset.Role.mapper.RoleMapper;
import com.t2m.g2nee.shop.bookset.Role.repository.RoleRepository;
import com.t2m.g2nee.shop.exception.NotFoundException;
import com.t2m.g2nee.shop.pageUtils.PageResponse;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class RoleService {

    private final RoleRepository roleRepository;
    private final RoleMapper mapper;

    /**
     * 역할 정보를 저장하는 메서드
     *
     * @param role 저장할 정보가 담긴 엔티티
     * @return 역할 객체 응답
     */
    public RoleDto.Response registerRole(Role role) {

        Role saveRole = roleRepository.save(role);

        return mapper.entityToDto(saveRole);
    }

    /**
     * 역할을 수정하는 메서드
     *
     * @param role 수정할 정보가 담긴 엔티티
     * @return 역할 객체 응답
     */
    public RoleDto.Response updateRole(Role role) {

        Role findRole = findRoleById(role.getRoleId());

        Optional.ofNullable(role.getRoleName()).ifPresent(findRole::setRoleName);

        Role saveRole = roleRepository.save(findRole);

        return mapper.entityToDto(saveRole);

    }

    /**
     * 역할 페이지를 구성하는 메서드
     *
     * @param page 현재 페이지
     * @return 페이징에 필요한 정보가 담긴 PageResponse 객체
     */
    public PageResponse<RoleDto.Response> getRoleList(int page) {

        int size = 10;
        Page<Role> rolePage =
                roleRepository.findAll(PageRequest.of(page - 1, size, Sort.by("roleName")));

        List<RoleDto.Response> responses = mapper.entitiesToDtos(rolePage.getContent());

//        int blockLimit = 3;
//        int startPage = (((int) Math.ceil(((double) pageable.getPageNumber() / blockLimit))) - 1) * blockLimit + 1;
//        int endPage = Math.min((startPage + blockLimit - 1), publisherPage.getTotalPages());

        return PageResponse.<RoleDto.Response>builder()
                .data(responses)
                .currentPage(page)
                .totalPage(rolePage.getTotalPages())
                .build();
    }

    /**
     * 역할을 삭제하는 메서드
     *
     * @param roleId 역할 Id
     */
    public void deleteRole(Long roleId) {

        findRoleById(roleId);

        roleRepository.deleteById(roleId);
    }

    /**
     * id에 해당하는 태그 객체를 확인하는 메서드
     *
     * @param roleId 역할 id
     * @return 역할 id에 해당하는 role entity
     */
    public Role findRoleById(Long roleId) {

        Optional<Role> optionalRole = roleRepository.findById(roleId);
        if (optionalRole.isPresent()) {

            return optionalRole.get();
        } else {
            throw new NotFoundException("역할 정보가 없습니다.");
        }
    }
}
