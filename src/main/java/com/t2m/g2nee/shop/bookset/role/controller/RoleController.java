package com.t2m.g2nee.shop.bookset.role.controller;

import com.t2m.g2nee.shop.bookset.role.domain.Role;
import com.t2m.g2nee.shop.bookset.role.dto.RoleDto;
import com.t2m.g2nee.shop.bookset.role.service.RoleService;
import com.t2m.g2nee.shop.pageUtils.PageResponse;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
@RequestMapping("/api/v1/shop/roles")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    /**
     * 역할을 생성하는 컨트롤러 입니다.
     *
     * @param request 역할 정보가 담긴 객체
     * @return 생성한 역할 정보
     */
    @PostMapping
    public ResponseEntity<RoleDto.Response> postRole(@RequestBody @Valid RoleDto.Request request) {

        Role role = Role.builder()
                .roleName(request.getRoleName())
                .build();

        RoleDto.Response response = roleService.registerRole(role);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * 역할 정보를 수정하는 컨트롤러 입니다.
     *
     * @param request 수정할 역할 정보가 담긴 객체
     * @param roleId  수정할 역할 id
     * @return 수정 후 역할 객체 정보
     */
    @PatchMapping("/{roleId}")
    public ResponseEntity<RoleDto.Response> modifyRole(@RequestBody @Valid RoleDto.Request request,
                                                       @PathVariable("roleId") Long roleId) {

        Role role = Role.builder()
                .roleId(roleId)
                .roleName(request.getRoleName())
                .build();

        RoleDto.Response response = roleService.updateRole(role);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    /**
     * 모든 역할을 조회하는 컨트롤러 입니다
     *
     * @return 역할 정보들과 페이지 정보
     */
    @GetMapping("/list")
    public ResponseEntity<List<RoleDto.Response>> getAllRole() {

        List<RoleDto.Response> response = roleService.getAllRole();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    /**
     * 역할 리스트를 보여주는 컨트롤러 입니다
     *
     * @param page 페이지 값
     * @return 역할 정보들과 페이지 정보
     */
    @GetMapping
    public ResponseEntity<PageResponse<RoleDto.Response>> getRoles(@RequestParam int page) {

        PageResponse<RoleDto.Response> response = roleService.getRoleList(page);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    /**
     * 역할 삭제하는 컨트롤러 입니다.
     *
     * @param roleId 역할 Id
     * @return X
     */
    @DeleteMapping("/{roleId}")
    public ResponseEntity deleteRole(@PathVariable("roleId") Long roleId) {

        roleService.deleteRole(roleId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}