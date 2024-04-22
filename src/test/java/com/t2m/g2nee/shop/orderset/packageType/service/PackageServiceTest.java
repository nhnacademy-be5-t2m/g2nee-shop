package com.t2m.g2nee.shop.orderset.packageType.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.t2m.g2nee.shop.exception.AlreadyExistException;
import com.t2m.g2nee.shop.exception.NotFoundException;
import com.t2m.g2nee.shop.orderset.packageType.dto.request.PackageSaveDto;
import com.t2m.g2nee.shop.orderset.packageType.dto.response.PackageInfoDto;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;


@SpringBootTest
@Transactional
class PackageServiceTest {

    @Autowired
    PackageService packageService;

    PackageInfoDto packageType1;
    PackageInfoDto packageType2;
    PackageInfoDto packageType3;


    @BeforeEach
    void setUp() {
        packageType1 = packageService.savePackage(new PackageSaveDto("포장지1", 1001, true));
        packageType2 = packageService.savePackage(new PackageSaveDto("포장지2", 1002, false));
        packageType3 = packageService.savePackage(new PackageSaveDto("포장지3", 1003, true));


    }

    @Test
    void testSave() {
        assertNotNull(packageType1.getPackageId());
        assertNotNull(packageType2.getPackageId());
        assertNotNull(packageType3.getPackageId());
    }

    @Test
    void testSaveFail() {
        assertThrows(AlreadyExistException.class, () -> {
            packageService.savePackage(
                    new PackageSaveDto("포장지1", 1001, true)
            );
        });
    }

    @Test
    void testUpdate() {
        PackageSaveDto request = new PackageSaveDto("포장지1", 1004, true);
        packageType1 = packageService.updatePackage(packageType1.getPackageId(), request);

        assertEquals(request.getName(), packageType1.getName());
        assertEquals(request.getPrice().longValue(), packageType1.getPrice());
        assertEquals(request.getIsActivated(), packageType1.getIsActivated());
    }

    @Test
    void testUpdateFail() {
        assertThrows(NotFoundException.class, () -> {
            packageService.updatePackage(1000000000L,
                    new PackageSaveDto("포장지1", 1004, true)
            );
        });
    }

    @Test
    void testGetPackage() {
        PackageInfoDto testPackage = packageService.getPackage(packageType1.getPackageId());

        assertEquals(testPackage.getPackageId(), packageType1.getPackageId());
        assertEquals(testPackage.getName(), packageType1.getName());
        assertEquals(testPackage.getPrice(), packageType1.getPrice());
        assertEquals(testPackage.getIsActivated(), packageType1.getIsActivated());
    }

    @Test
    void testGetPackageFail() {
        assertThrows(NotFoundException.class, () -> packageService.getPackage(1000000000L));
    }

    @Test
    void tesGetAllPackages() {
        List<PackageInfoDto> page = packageService.getAllPackages(1).getData();

        assertThat(page).isNotNull().hasSizeGreaterThan(3);
    }

    @Test
    void testSoftDeletePackage() {
        packageService.softDeletePackage(packageType1.getPackageId());
        assertFalse(packageService.getPackage(packageType1.getPackageId()).getIsActivated());
    }

    @Test
    void testSoftDeletePackageFail() {
        assertThrows(NotFoundException.class, () -> packageService.softDeletePackage(1000000000L));

    }

    @Test
    void testActivatedPackage() {
        packageService.activatedPackage(packageType2.getPackageId());
        assertTrue(packageService.getPackage(packageType2.getPackageId()).getIsActivated());
    }

    @Test
    void testActivatedPackageFail() {
        assertThrows(NotFoundException.class, () -> packageService.activatedPackage(1000000000L));

    }
}
