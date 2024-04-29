package com.t2m.g2nee.shop.orderset.packagetype.service.impl;

import com.t2m.g2nee.shop.exception.AlreadyExistException;
import com.t2m.g2nee.shop.exception.NotFoundException;
import com.t2m.g2nee.shop.orderset.packagetype.domain.PackageType;
import com.t2m.g2nee.shop.orderset.packagetype.dto.request.PackageSaveDto;
import com.t2m.g2nee.shop.orderset.packagetype.dto.response.PackageInfoDto;
import com.t2m.g2nee.shop.orderset.packagetype.repository.PackageRepository;
import com.t2m.g2nee.shop.orderset.packagetype.service.PackageService;
import com.t2m.g2nee.shop.pageUtils.PageResponse;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * PackageService의 구현체 입니다.
 *
 * @author : 김수빈
 * @since : 1.0
 */
@Service
@Transactional
public class PackageServiceImpl implements PackageService {

    private static final int MAXPAGEBUTTONS = 5;
    private final PackageRepository packageRepository;

    /**
     * PackageServiceImp의 생성자 입니다.
     * @param packageRepository 패키지 레포지토리
     */
    public PackageServiceImpl(PackageRepository packageRepository) {
        this.packageRepository = packageRepository;
    }

    /**
     * {@inheritDoc}
     * @throws AlreadyExistException 포장지 이름이 중복될 때
     */
    @Override
    public PackageInfoDto savePackage(PackageSaveDto request) {
        //포장지 이름이 존재하는지 확인
        if (packageRepository.existsByName(request.getName())) {
            //존재하면 예외
            throw new AlreadyExistException("이미 존재하는 포장지 이름 입니다.");
        }
        //존재하지 않으면 저장
        return convertToPackageInfoDto(packageRepository.save(convertToPackageType(request)));
    }

    /**
     * {@inheritDoc}
     * @throws NotFoundException 수정할 포장지 id가 유효하지 않을 때
     * @throws AlreadyExistException 수정하는 이름이 이미 존재하는 포장지 이름일 때
     */
    @Override
    public PackageInfoDto updatePackage(Long packageId, PackageSaveDto request) {
        //포장지가 있는지 확인
        if (!packageRepository.existsById(packageId)) {
            //없으면 예외
            throw new NotFoundException("수정할 포장지를 찾을 수 없습니다.");
        } else if (packageRepository.existsByNameAndPackageIdNot(request.getName(), packageId)) {
            throw new AlreadyExistException("이미 존재하는 포장지 이름 입니다.");
        } else {
            //있으면 수정
            return convertToPackageInfoDto(packageRepository.save(new PackageType(
                    packageId, request.getName(), BigDecimal.valueOf(request.getPrice()), request.getIsActivated())));
        }
    }

    /**
     * {@inheritDoc}
     * @throws NotFoundException 유효한 포장지 id가 아닐 때
     */
    @Override
    @Transactional(readOnly = true)
    public PackageInfoDto getPackage(Long packageId) {
        //특정 포장지를 가져와 없으면 예외
        return convertToPackageInfoDto(
                packageRepository.findById(packageId).orElseThrow(() -> new NotFoundException("존재하지 않는 포장지입니다.")));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public PageResponse<PackageInfoDto> getAllPackages(int page) {
        //페이징 처리된 포장지 목록을 가져옴
        //활성화된 포장지를 먼저 보여주고, 그 다음은 가격이 낮은 순으로 보여줌
        Page<PackageType> packageTypes = packageRepository.findAll(
                PageRequest.of(page - 1, 10, Sort.by("isActivated").descending()
                        .and(Sort.by("price")))
        );

        List<PackageInfoDto> packageInfoDtoList = packageTypes
                .stream().map(this::convertToPackageInfoDto)
                .collect(Collectors.toList());

        int startPage = (int) Math.max(1, packageTypes.getNumber() - Math.floor((double) MAXPAGEBUTTONS / 2));
        int endPage = Math.min(startPage + MAXPAGEBUTTONS - 1, packageTypes.getTotalPages());

        if (endPage - startPage + 1 < MAXPAGEBUTTONS) {
            startPage = Math.max(1, endPage - MAXPAGEBUTTONS + 1);
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

    /**
     * {@inheritDoc}
     * @throws NotFoundException 유효한 포장지 id가 없을 경우
     */
    @Override
    public boolean softDeletePackage(Long packageId) {
        //포장지가 존재하면서 활성되어 있는지 확인
        if (packageRepository.getExistsByPackageIdAndIsActivated(packageId, true)) {
            //soft delete
            packageRepository.softDeleteByPackageId(packageId);
            return false;
        } else {
            throw new NotFoundException("삭제할 포장지를 찾을 수 없습니다.");
        }
    }

    /**
     * {@inheritDoc}
     * @throws NotFoundException 유효한 포장지 id가 없을 꼉우
     */
    @Override
    public boolean activatedPackage(Long packageId) {
        //포장지가 존재하면서 비활성화인지 확인
        if (packageRepository.getExistsByPackageIdAndIsActivated(packageId, false)) {
            //포장지 활성화
            packageRepository.activateByPackageId(packageId);
            return true;
        } else {
            throw new NotFoundException("활성화할 포장지를 찾을 수 없습니다.");
        }
    }

    /**
     * PackageType 객체를 PackageInfoDto로 변환
     *
     * @param packageType
     * @return PackageInfoDto
     */
    private PackageInfoDto convertToPackageInfoDto(PackageType packageType) {
        return new PackageInfoDto(packageType.getPackageId(), packageType.getName(),
                packageType.getPrice().intValue(), packageType.getIsActivated());
    }

    /**
     * PackageSaveDto객체를 PackageType으로 변환
     *
     * @param packageSaveDto
     * @return PackageType
     */
    private PackageType convertToPackageType(PackageSaveDto packageSaveDto) {
        return new PackageType(packageSaveDto.getName(),
                BigDecimal.valueOf(packageSaveDto.getPrice()), packageSaveDto.getIsActivated());
    }
}
