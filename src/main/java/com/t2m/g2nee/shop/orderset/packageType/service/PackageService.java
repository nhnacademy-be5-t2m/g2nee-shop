package com.t2m.g2nee.shop.orderset.packageType.service;

import com.t2m.g2nee.shop.orderset.packageType.dto.request.PackageSaveDto;
import com.t2m.g2nee.shop.orderset.packageType.dto.response.PackageInfoDto;
import com.t2m.g2nee.shop.pageUtils.PageResponse;

public interface PackageService {

    PackageInfoDto savePackage(PackageSaveDto request);

    PackageInfoDto updatePackage(Long packageId, PackageSaveDto request);

    PackageInfoDto getPackage(Long packageId);

    PageResponse<PackageInfoDto> getAllPackages(int page);

    boolean softDeletePackage(Long packageId);

    boolean activatedPackage(Long packageId);

}
