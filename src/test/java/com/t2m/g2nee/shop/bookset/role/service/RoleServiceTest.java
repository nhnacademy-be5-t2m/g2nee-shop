package com.t2m.g2nee.shop.bookset.role.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.t2m.g2nee.shop.bookset.role.domain.Role;
import com.t2m.g2nee.shop.bookset.role.dto.RoleDto;
import com.t2m.g2nee.shop.bookset.role.mapper.RoleMapper;
import com.t2m.g2nee.shop.bookset.role.repository.RoleRepository;
import com.t2m.g2nee.shop.exception.NotFoundException;
import com.t2m.g2nee.shop.pageUtils.PageResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@ExtendWith(MockitoExtension.class)
class RoleServiceTest {

    @Mock
    private RoleRepository roleRepository;
    @Mock
    private RoleMapper mapper;

    @InjectMocks
    private RoleService roleService;

    @Test
    @DisplayName("역할 등록 테스트")
    void registerRole() {

        //given
        RoleDto.Request request = getRequest();
        Role role = getRole();
        RoleDto.Response response = getResponse();

        when(roleRepository.save(role)).thenReturn(role);
        when(mapper.entityToDto(role)).thenReturn(response);

        //when
        roleService.registerRole(role);

        // then
        assertEquals(response.getRoleName(), request.getRoleName());

    }

    @Test
    @DisplayName("역할 수정 테스트")
    void updateRole() {

        //given
        RoleDto.Request request = getModifyRequest();
        Role role = getModifiedRole();
        RoleDto.Response response = getModifiedResponse();

        when(roleRepository.findById(role.getRoleId())).thenReturn(Optional.of(role));
        when(roleRepository.save(role)).thenReturn(role);

        //when
        roleService.updateRole(role);

        //then
        assertEquals(response.getRoleName(), request.getRoleName());

    }

    @Test
    @DisplayName("역할 리스트 조회 테스트")
    void getRoleList() {

        //given
        List<Role> roleList = getList();
        List<RoleDto.Response> responseList = getResponseList();
        PageResponse<RoleDto.Response> responses = PageResponse.<RoleDto.Response>builder()
                .data(responseList)
                .currentPage(1)
                .totalPage(2)
                .build();

        Pageable pageable = PageRequest.of(0, 10, Sort.by("roleName"));

        Page<Role> rolePage = new PageImpl<>(roleList, pageable, roleList.size());

        when(roleRepository.findAll(PageRequest.of(0, 10, Sort.by("roleName"))))
                .thenReturn(rolePage);
        when(mapper.entitiesToDtos(roleList)).thenReturn(responseList);

        //when
        roleService.getRoleList(1);

        //then
        assertEquals(10, responses.getData().size());
        assertEquals(1, responses.getCurrentPage());
        assertEquals(2, responses.getTotalPage());
        assertEquals(20, rolePage.getTotalElements());
        for (int i = 0; i < responseList.size(); i++) {
            assertEquals(responseList.get(i).getRoleName(), responses.getData().get(i).getRoleName());
        }
    }

    @Test
    @DisplayName("역할 삭제 테스트")
    void deleteRole() {

        //given
        Role role = getRole();

        when(roleRepository.findById(role.getRoleId())).thenReturn(Optional.of(role));
        doNothing().when(roleRepository).deleteById(role.getRoleId());

        //when //then
        roleService.deleteRole(role.getRoleId());

        verify(roleRepository, times(1)).deleteById(role.getRoleId());

    }
    @Test
    @DisplayName("역할이 없을 때 예외 테스트")
    void testExistRole(){
        Role role = getRole();

        when(roleRepository.findById(role.getRoleId())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> roleService.deleteRole(role.getRoleId()));

    }

    private List<Role> getList() {

        List<Role> roleList = new ArrayList<>();
        for (int i = 1; i < 21; i++) {
            Role role = Role.builder()
                    .roleId((long) i)
                    .roleName("역할" + i)
                    .build();

            roleList.add(role);
        }
        return roleList;
    }

    private List<RoleDto.Response> getResponseList() {

        List<RoleDto.Response> roleList = new ArrayList<>();
        for (int i = 1; i < 11; i++) {
            RoleDto.Response role = RoleDto.Response.builder()
                    .roleId((long) i)
                    .roleName("역할" + i)

                    .build();

            roleList.add(role);
        }
        return roleList;
    }

    private RoleDto.Request getRequest() {

        return RoleDto.Request.builder()
                .roleName("역할1")
                .build();
    }

    private RoleDto.Request getModifyRequest() {


        return RoleDto.Request.builder()
                .roleName("역할2")
                .build();
    }

    private Role getRole() {

        return Role.builder()
                .roleId(1L)
                .roleName("역할1")
                .build();
    }

    private Role getModifiedRole() {

        return Role.builder()
                .roleId(1L)
                .roleName("역할2")
                .build();
    }


    private RoleDto.Response getResponse() {

        return RoleDto.Response.builder()
                .roleId(1L)
                .roleName("역할1")
                .build();
    }

    private RoleDto.Response getModifiedResponse() {

        return RoleDto.Response.builder()
                .roleId(1L)
                .roleName("역할2")
                .build();
    }
}
