package com.t2m.g2nee.shop.orderset.packageType.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.t2m.g2nee.shop.config.ElasticsearchConfig;
import com.t2m.g2nee.shop.config.MapperConfig;
import com.t2m.g2nee.shop.orderset.packageType.repository.PackageRepository;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

@DataJpaTest
@ActiveProfiles("test")
@TestPropertySource(locations = "classpath:application-test.properties")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(value = {MapperConfig.class, ElasticsearchConfig.class})
public class PackageTypeTest {

    @Autowired
    PackageRepository packageRepository;

    @Test
    void test() {
        PackageType packageType = new PackageType("포장지", BigDecimal.valueOf(1000), true);

        packageRepository.saveAndFlush(packageType);

        PackageType testPackageType = packageRepository.findById(packageType.getPackageId()).orElse(null);

        assertThat(testPackageType).isEqualTo(packageType);
        assertThat(testPackageType.getPackageId()).isEqualTo(packageType.getPackageId());
        assertThat(testPackageType.getName()).isEqualTo(packageType.getName());
        assertThat(testPackageType.getPrice()).isEqualTo(packageType.getPrice());
        assertThat(testPackageType.getIsActivated()).isTrue();
    }
}
