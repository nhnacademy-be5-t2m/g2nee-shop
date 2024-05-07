package com.t2m.g2nee.shop.orderset.packagetype.controller;

import com.t2m.g2nee.shop.orderset.packagetype.dto.request.PackageSaveDto;
import com.t2m.g2nee.shop.orderset.packagetype.dto.response.PackageInfoDto;
import com.t2m.g2nee.shop.orderset.packagetype.service.PackageService;
import com.t2m.g2nee.shop.pageUtils.PageResponse;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * 포장지에 대한 컨트롤러 입니다.
 *
 * @author : 김수빈
 * @since : 1.0
 */
@RestController
@RequestMapping("/api/v1/shop/packages")
public class PackageController {

    private final PackageService packageService;

    /**
     * PackageController의 생성자입니다.
     *
     * @param packageService 패키지를 CURD하기 위한 서비스
     */
    public PackageController(PackageService packageService) {
        this.packageService = packageService;
    }

    /**
     * 포장지를 저장하는 컨트롤러입니다.
     *
     * @param request 포장지 저장 객체
     * @return ResponseEntity<PackageInfoDto>
     */
    @PostMapping
    public ResponseEntity<PackageInfoDto> createPackage(@RequestPart MultipartFile image,
                                                        @RequestPart @Valid PackageSaveDto request) {
        return ResponseEntity.status(HttpStatus.CREATED).contentType(MediaType.APPLICATION_JSON)
                .body(packageService.savePackage(image, request));
    }

    /**
     * 포장지를 수정하는 컨트롤러입니다.
     *
     * @param packageId 수정할 포장지 id
     * @param request   포장지 수정 객체
     * @return ResponseEntity<PackageInfoDto>
     */
    @PutMapping("/{packageId}")
    public ResponseEntity<PackageInfoDto> updatePackage(@PathVariable("packageId") Long packageId,
                                                        @RequestPart(required = false) MultipartFile image,
                                                        @RequestPart @Valid PackageSaveDto request) {
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON)
                .body(packageService.updatePackage(packageId, image, request));
    }

    /**
     * 특정 포장지 하나를 얻는 컨트롤러 입니다.
     *
     * @param packageId 포장지 id
     * @return ResponseEntity<PackageInfoDto>
     */
    @GetMapping("/{packageId}")
    public ResponseEntity<PackageInfoDto> getPackage(@PathVariable("packageId") Long packageId) {
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON)
                .body(packageService.getPackage(packageId));
    }

    /**
     * 모든 포장지를 페이징하여 반환하는 컨트롤러 입니다.
     *
     * @param page 현재 페이지
     * @return ResponseEntity<PageResponse < PackageInfoDto>>
     */
    @GetMapping
    public ResponseEntity<PageResponse<PackageInfoDto>> getAllPackage(@RequestParam int page) {
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON)
                .body(packageService.getAllPackages(page));
    }

    /**
     * 활성화된 모든 포장지를 페이징하여 반환하는 컨트롤러 입니다.
     *
     * @param page 현재 페이지
     * @return ResponseEntity<PageResponse < PackageInfoDto>>
     */
    @GetMapping("/activated")
    public ResponseEntity<PageResponse<PackageInfoDto>> getActivatePackage(@RequestParam int page) {
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON)
                .body(packageService.getActivatePackages(page));
    }

    /**
     * 포장지를 soft delete하는 컨트롤러 입니다.
     *
     * @param packageId 포장지 id
     * @return ResponseEntity<Boolean> 성공 시 false 반환
     */
    @PatchMapping("/delete/{packageId}")
    public ResponseEntity<Boolean> deletePackage(@PathVariable("packageId") Long packageId) {
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON)
                .body(packageService.softDeletePackage(packageId));
    }

    /**
     * 비활성된 포장지를 활성화 하는 컨트롤러 입니다.
     *
     * @param packageId 포장지 id
     * @return ResponseEntity<Boolean> 성공 시 ture 반환
     */
    @PatchMapping("/activate/{packageId}")
    public ResponseEntity<Boolean> activatePackage(@PathVariable("packageId") Long packageId) {
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON)
                .body(packageService.activatedPackage(packageId));
    }

}
