package com.t2m.g2nee.shop.orderset.packagetype.service;

import com.t2m.g2nee.shop.orderset.packagetype.dto.request.PackageSaveDto;
import com.t2m.g2nee.shop.orderset.packagetype.dto.response.PackageInfoDto;
import com.t2m.g2nee.shop.pageUtils.PageResponse;
import org.springframework.web.multipart.MultipartFile;

/**
 * 포장지를 저장, 수정, 삭제, 읽기 등을 할 수있게 하는 서비스입니다.
 *
 * @author : 김수빈
 * @since : 1.0
 */
public interface PackageService {

    /**
     * 포장지를 저장합니다.
     *
     * @param request 포장지 저장 객체
     * @param image   포장지 이미지
     * @return PackageInfoDto
     */
    PackageInfoDto savePackage(MultipartFile image, PackageSaveDto request);

    /**
     * 포장지를 수정합니다.
     *
     * @param packageId 포장지 id
     * @param request   포장지 수정 객체
     * @return PackageInfoDto
     */
    PackageInfoDto updatePackage(Long packageId, MultipartFile image, PackageSaveDto request);

    /**
     * 하나의 포장지를 얻어옵니다.
     *
     * @param packageId 포장지 id
     * @return PackageInfoDto
     */
    PackageInfoDto getPackage(Long packageId);

    /**
     * 모든 포장지를 페이징처리하여 반환합니다.
     *
     * @param page 현재 페이지
     * @return PageResponse<PackageInfoDto>
     */
    PageResponse<PackageInfoDto> getAllPackages(int page);

    /**
     * 활성화된 모든 포장지를 페이징처리하여 반환합니다.
     *
     * @param page 현재 페이지
     * @return PageResponse<PackageInfoDto>
     */
    PageResponse<PackageInfoDto> getActivatePackages(int page);

    /**
     * 포장지를 soft delete합니다.
     *
     * @param packageId 포장지 id
     * @return boolean
     */
    boolean softDeletePackage(Long packageId);

    /**
     * 비활성화인 포장지를 활성화합니다.
     *
     * @param packageId 포장지 id
     * @return boolean
     */
    boolean activatedPackage(Long packageId);

}
