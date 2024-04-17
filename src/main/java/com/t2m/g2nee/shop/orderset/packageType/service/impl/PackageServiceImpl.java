package com.t2m.g2nee.shop.orderset.packageType.service.impl;

import com.t2m.g2nee.shop.exception.AlreadyExistException;
import com.t2m.g2nee.shop.exception.NotFoundException;
import com.t2m.g2nee.shop.orderset.packageType.domain.PackageType;
import com.t2m.g2nee.shop.orderset.packageType.dto.request.PackageSaveDto;
import com.t2m.g2nee.shop.orderset.packageType.dto.response.PackageInfoDto;
import com.t2m.g2nee.shop.orderset.packageType.repository.PackageRepository;
import com.t2m.g2nee.shop.orderset.packageType.service.PackageService;
import com.t2m.g2nee.shop.pageUtils.PageResponse;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PackageServiceImpl implements PackageService {

    private static int maxPageButtons = 5;
    private final PackageRepository packageRepository;

    public PackageServiceImpl(PackageRepository packageRepository) {
        this.packageRepository = packageRepository;
    }

    @Override
    public PackageInfoDto savePackage(PackageSaveDto request) {
        if (packageRepository.existsByName(request.getName())) {
            throw new AlreadyExistException("이미 존재하는 포장지 이름 입니다.");
        }

        return convertToPackageInfoDto(packageRepository.save(convertToPackageType(request)));
    }

    @Override
    public PackageInfoDto updatePackage(Long packageId, PackageSaveDto request) {
        if (packageRepository.existsById(packageId)) {
            return convertToPackageInfoDto(packageRepository.save(convertToPackageType(request)));
        } else {
            throw new NotFoundException("수정할 포장지를 찾을 수 없습니다.");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public PackageInfoDto getPackage(Long packageId) {
        return convertToPackageInfoDto(
                packageRepository.findById(packageId).orElseThrow(() -> new NotFoundException("존재하지 않는 포장지입니다.")));
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<PackageInfoDto> getAllPackages(int page) {
        Page<PackageType> packageTypes = packageRepository.findAll(
                PageRequest.of(page - 1, 10, Sort.by("price"))
        );

        List<PackageInfoDto> packageInfoDtoList = packageTypes
                .stream().map(this::convertToPackageInfoDto)
                .collect(Collectors.toList());

        int startPage = (int) Math.max(1, packageTypes.getNumber() - Math.floor((double) maxPageButtons / 2));
        int endPage = Math.min(startPage + maxPageButtons - 1, packageTypes.getTotalPages());

        if (endPage - startPage + 1 < maxPageButtons) {
            startPage = Math.max(1, endPage - maxPageButtons + 1);
        }


        return PageResponse.<PackageInfoDto>builder()
                .data(packageInfoDtoList)
                .currentPage(page)
                .totalPage(packageTypes.getTotalPages())
                .startPage(startPage)
                .endPage(endPage)
                .totalElements(packageTypes.getTotalElements())
                .build();
    }

    @Override
    public boolean softDeletePackage(Long packageId) {
        if (packageRepository.getExistsByPackageIdAndIsActivated(packageId, true)) {
            packageRepository.softDeleteByPackageId(packageId);
            return packageRepository.findById(packageId).orElseThrow(() -> new NotFoundException("포장지를 찾을 수 없습니다"))
                    .getIsActivated();
        } else {
            throw new NotFoundException("삭제할 포장지를 찾을 수 없습니다.");
        }
    }

    @Override
    public boolean activatedPackage(Long packageId) {
        if (packageRepository.getExistsByPackageIdAndIsActivated(packageId, true)) {
            packageRepository.activateByPackageId(packageId);
            return packageRepository.findById(packageId).orElseThrow(() -> new NotFoundException("포장지를 찾을 수 없습니다"))
                    .getIsActivated();
        } else {
            throw new NotFoundException("활성화할 포장지를 찾을 수 없습니다.");
        }
    }

    private PackageInfoDto convertToPackageInfoDto(PackageType packageType) {
        return new PackageInfoDto(packageType.getPackageId(), packageType.getName(),
                packageType.getPrice().intValue(), packageType.getIsActivated());
    }

    private PackageType convertToPackageType(PackageSaveDto packageSaveDto) {
        return new PackageType(packageSaveDto.getName(),
                BigDecimal.valueOf(packageSaveDto.getPrice()), packageSaveDto.getIsActivated());
    }
}
