package com.t2m.g2nee.shop.policyset.pointpolicy.controller;

import com.t2m.g2nee.shop.pageUtils.PageResponse;
import com.t2m.g2nee.shop.policyset.pointpolicy.dto.request.PointPolicySaveDto;
import com.t2m.g2nee.shop.policyset.pointpolicy.dto.response.PointPolicyInfoDto;
import com.t2m.g2nee.shop.policyset.pointpolicy.service.PointPolicyService;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 포인트 정책에 대한 컨트롤러입니다.
 *
 * @author : 김수빈
 * @since : 1.0
 */
@RestController
@RequestMapping("/api/v1/shop/pointPolicies")
public class PointPolicyController {

    private final PointPolicyService pointPolicyService;

    public PointPolicyController(PointPolicyService pointPolicyService) {
        this.pointPolicyService = pointPolicyService;
    }

    /**
     * 포인트 정책을 저장합니다.
     *
     * @param request 포인트 정책 저장 객체
     * @return ResponseEntity<PointPolicyInfoDto>
     */
    @PostMapping
    public ResponseEntity<PointPolicyInfoDto> createPointPolicy(@RequestBody @Valid PointPolicySaveDto request) {
        return ResponseEntity.status(HttpStatus.CREATED).contentType(MediaType.APPLICATION_JSON)
                .body(pointPolicyService.savePointPolicy(request));
    }

    /**
     * 포인트 정책을 수정합니다.
     *
     * @param pointPolicyId 수정할 포인트 정책 id
     * @param request       포인트 정책 수정 객체
     * @return ResponseEntity<PointPolicyInfoDto>
     */
    @PutMapping("/{pointPolicyId}")
    public ResponseEntity<PointPolicyInfoDto> updatePointPolicy(@PathVariable("pointPolicyId") Long pointPolicyId,
                                                                @RequestBody @Valid PointPolicySaveDto request) {
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON)
                .body(pointPolicyService.updatePointPolicy(pointPolicyId, request));
    }

    /**
     * 포인트 정책을 삭제 합니다.
     *
     * @param pointPolicyId 포인트 정책 id
     * @return ResponseEntity<Boolean> 성공시 false를 반환합니다.
     */
    @PatchMapping("/{pointPolicyId}")
    public ResponseEntity<Boolean> deletePointPolicy(@PathVariable("pointPolicyId") Long pointPolicyId) {
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON)
                .body(pointPolicyService.softDeletePointPolicy(pointPolicyId));
    }

    /**
     * 한 포인트 정책을 반환합니다.
     *
     * @param pointPolicyId 포인트 정책 id
     * @return ResponseEntity<PointPolicyInfoDto>
     */
    @GetMapping("/{pointPolicyId}")
    public ResponseEntity<PointPolicyInfoDto> getPointPolicy(@PathVariable("pointPolicyId") Long pointPolicyId) {
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON)
                .body(pointPolicyService.getPointPolicy(pointPolicyId));
    }

    /**
     * 모든 포인트 정책을 페이징 하여 반환합니다.
     *
     * @param page 현재 페이지
     * @return ResponseEntity<PageResponse < PointPolicyInfoDto>>
     */
    @GetMapping
    public ResponseEntity<PageResponse<PointPolicyInfoDto>> getAllPointPolicy(@RequestParam int page) {
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON)
                .body(pointPolicyService.getAllPointPolicy(page));
    }
}
