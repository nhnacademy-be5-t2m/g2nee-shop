package com.t2m.g2nee.shop.orderset.packageType.controller;

import com.t2m.g2nee.shop.orderset.packageType.dto.request.PackageSaveDto;
import com.t2m.g2nee.shop.orderset.packageType.dto.response.PackageInfoDto;
import com.t2m.g2nee.shop.orderset.packageType.service.PackageService;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/shop/package")
public class PackageController {

    private final PackageService packageService;

    public PackageController(PackageService packageService) {
        this.packageService = packageService;
    }

    @PostMapping
    public ResponseEntity<PackageInfoDto> createPackage(@RequestBody @Valid PackageSaveDto request) {
        return ResponseEntity.status(HttpStatus.CREATED).contentType(MediaType.APPLICATION_JSON)
                .body(packageService.savePackage(request));
    }

    @PutMapping("/{packageId}")
    public ResponseEntity<PackageInfoDto> updatePackage(@PathVariable("packageId") Long packageId,
                                                        @RequestBody @Valid PackageSaveDto request) {
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON)
                .body(packageService.updatePackage(packageId, request));
    }

    @GetMapping("/{packageId}")
    public ResponseEntity<PackageInfoDto> getPackage(@PathVariable("packageId") Long packageId) {
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON)
                .body(packageService.getPackage(packageId));
    }

    @GetMapping
    public ResponseEntity<PageResponse<PackageInfoDto>> getAllPackage(@RequestParam int page) {
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON)
                .body(packageService.getAllPackages(page));
    }

    @PatchMapping("/delete/{packageId}")
    public ResponseEntity<Boolean> deletePackage(@PathVariable("packageId") Long packageId) {
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON)
                .body(packageService.softDeletePackage(packageId));
    }

    @PatchMapping("activate/{packageId}")
    public ResponseEntity<Boolean> activatePackage(@PathVariable("packageId") Long packageId) {
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON)
                .body(packageService.activatedPackage(packageId));
    }

}
