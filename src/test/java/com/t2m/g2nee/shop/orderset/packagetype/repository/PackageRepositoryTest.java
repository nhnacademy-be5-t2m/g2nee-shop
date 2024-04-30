package com.t2m.g2nee.shop.orderset.packagetype.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import com.t2m.g2nee.shop.config.ElasticsearchConfig;
import com.t2m.g2nee.shop.config.MapperConfig;
import com.t2m.g2nee.shop.orderset.packagetype.domain.PackageType;
import java.math.BigDecimal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

@DataJpaTest
@ActiveProfiles("test")
@TestPropertySource(locations = "classpath:application-test.properties")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(value = {MapperConfig.class, ElasticsearchConfig.class})
class PackageRepositoryTest {

    @Autowired
    PackageRepository packageRepository;

    PackageType packageType1;
    PackageType packageType2;
    PackageType packageType3;


    @BeforeEach
    void setUp() {
        packageType1 = packageRepository.save(new PackageType("포장지1", BigDecimal.valueOf(1001), true));
        packageType2 = packageRepository.save(new PackageType("포장지2", BigDecimal.valueOf(1002), false));
        packageType3 = packageRepository.save(new PackageType("포장지3", BigDecimal.valueOf(1003), true));

    }

    @Test
    void testExistsById() {
        assertTrue(packageRepository.existsById(packageType1.getPackageId()));
        assertTrue(packageRepository.existsById(packageType2.getPackageId()));
        assertTrue(packageRepository.existsById(packageType3.getPackageId()));
    }

    @Test
    void testSave() {
        assertNotNull(packageRepository.findById(packageType1.getPackageId()));
        assertNotNull(packageRepository.findById(packageType2.getPackageId()));
        assertNotNull(packageRepository.findById(packageType3.getPackageId()));
    }

    @Test
    void testExistsByName() {
        assertTrue(packageRepository.existsByName("포장지1"));
    }

    @Test
    void testFindAll() {
        Page<PackageType> packageTypes = packageRepository.findAll(
                PageRequest.of(0, 10, Sort.by("isActivated").descending()
                        .and(Sort.by("price")))
        );

        assertThat(packageTypes).isNotNull().contains(packageType1, packageType3, packageType2);
    }

    @Test
    void testGetExistsByPackageIdAndIsActivated() {
        assertTrue(packageRepository.getExistsByPackageIdAndIsActivated(packageType1.getPackageId(), true));
        assertTrue(packageRepository.getExistsByPackageIdAndIsActivated(packageType2.getPackageId(), false));

    }

    @Test
    void testSoftDeleteByPackageId() {
        packageRepository.softDeleteByPackageId(packageType1.getPackageId());
        assertFalse(packageRepository.findById(packageType1.getPackageId()).orElse(null).getIsActivated());
    }

    @Test
    void testActivatedPackage() {
        packageRepository.activateByPackageId(packageType2.getPackageId());
        assertTrue(packageRepository.findById(packageType2.getPackageId()).orElse(null).getIsActivated());
    }
}
