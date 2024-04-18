package com.t2m.g2nee.shop.policyset.pointPolicy.controller;

import com.t2m.g2nee.shop.pageUtils.PageResponse;
import com.t2m.g2nee.shop.policyset.pointPolicy.dto.request.PointPolicySaveDto;
import com.t2m.g2nee.shop.policyset.pointPolicy.dto.response.PointPolicyInfoDto;
import com.t2m.g2nee.shop.policyset.pointPolicy.service.PointPolicyService;
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

@RestController
@RequestMapping("/shop/pointPolicy")
public class PointPolicyController {

    private final PointPolicyService pointPolicyService;

    public PointPolicyController(PointPolicyService pointPolicyService) {
        this.pointPolicyService = pointPolicyService;
    }

    @PostMapping
    public ResponseEntity<PointPolicyInfoDto> createPointPolicy(@RequestBody @Valid PointPolicySaveDto request) {
        return ResponseEntity.status(HttpStatus.CREATED).contentType(MediaType.APPLICATION_JSON)
                .body(pointPolicyService.savePointPolicy(request));
    }

    @PutMapping("/{pointPolicyId}")
    public ResponseEntity<PointPolicyInfoDto> updatePointPolicy(@PathVariable("pointPolicyId") Long pointPolicyId,
                                                                @RequestBody @Valid PointPolicySaveDto request) {
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON)
                .body(pointPolicyService.updatePointPolicy(pointPolicyId, request));
    }

    @PatchMapping("/{pointPolicyId}")
    public ResponseEntity<Boolean> deletePointPolicy(@PathVariable("pointPolicyId") Long pointPolicyId) {
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON)
                .body(pointPolicyService.softDeletePointPolicy(pointPolicyId));
    }

    @GetMapping("/{pointPolicyId}")
    public ResponseEntity<PointPolicyInfoDto> getDeliveryPolicy(@PathVariable("pointPolicyId") Long pointPolicyId) {
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON)
                .body(pointPolicyService.getPointPolicy(pointPolicyId));
    }

    @GetMapping
    public ResponseEntity<PageResponse<PointPolicyInfoDto>> getAllPointPolicy(@RequestParam int page) {
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON)
                .body(pointPolicyService.getAllPointPolicy(page));
    }
}
