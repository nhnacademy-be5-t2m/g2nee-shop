package com.t2m.g2nee.shop.bookset.role.service;

import com.t2m.g2nee.shop.bookset.role.domain.Role;
import com.t2m.g2nee.shop.bookset.role.dto.RoleDto;
import com.t2m.g2nee.shop.bookset.role.mapper.RoleMapper;
import com.t2m.g2nee.shop.bookset.role.repository.RoleRepository;
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

        // 역할 이름이 존재하는지 확인 후 있으면 상태를 변경하고 없으면 저장합니다.
        Optional<Role> optionalRole = roleRepository.findByRoleName(role.getRoleName());

        if (optionalRole.isPresent()) {

            Role findRole = optionalRole.get();
            findRole.setActivated(true);

            return mapper.entityToDto(findRole);
        } else {

            role.setActivated(true);
            Role saveRole = roleRepository.save(role);

            return mapper.entityToDto(saveRole);
        }
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
     * 모든 역할을 조회하는 메서드 입니다
     */
    @Transactional(readOnly = true)
    public List<RoleDto.Response> getAllRole() {

        return mapper.entitiesToDtos(roleRepository.findAllActivated());
    }

    /**
     * 역할 페이지를 구성하는 메서드
     *
     * @param page 현재 페이지
     * @return 페이징에 필요한 정보가 담긴 PageResponse 객체
     */
    @Transactional(readOnly = true)
    public PageResponse<RoleDto.Response> getRoleList(int page) {

        int size = 10;
        Page<Role> rolePage =
                roleRepository.findAllActivated(PageRequest.of(page - 1, size, Sort.by("roleName")));

        List<RoleDto.Response> responses = mapper.entitiesToDtos(rolePage.getContent());

        int maxPageButtons = 5;
        int startPage = (int) Math.max(1, rolePage.getNumber() - Math.floor((double) maxPageButtons / 2));
        int endPage = Math.min(startPage + maxPageButtons - 1, rolePage.getTotalPages());

        if (endPage - startPage + 1 < maxPageButtons) {
            startPage = Math.max(1, endPage - maxPageButtons + 1);
        }


        return PageResponse.<RoleDto.Response>builder()
                .data(responses)
                .currentPage(page)
                .startPage(startPage)
                .endPage(endPage)
                .totalPage(rolePage.getTotalPages())
                .totalElements(rolePage.getTotalElements())
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
