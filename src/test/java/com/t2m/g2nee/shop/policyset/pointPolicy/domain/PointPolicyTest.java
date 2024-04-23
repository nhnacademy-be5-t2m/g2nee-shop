package com.t2m.g2nee.shop.policyset.pointPolicy.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.t2m.g2nee.shop.config.ElasticsearchConfig;
import com.t2m.g2nee.shop.config.MapperConfig;
import com.t2m.g2nee.shop.policyset.pointPolicy.repository.PointPolicyRepository;
import java.math.BigDecimal;
import org.junit.jupiter.api.DisplayName;
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
class PointPolicyTest {
    @Autowired
    PointPolicyRepository pointPolicyRepository;

    @Test
    @DisplayName("PontPolicy Entity test")
    void test() {
        PointPolicy pointPolicy = new PointPolicy("테스트 정책", "PERCENT", BigDecimal.valueOf(0.25));

        pointPolicyRepository.saveAndFlush(pointPolicy);

        PointPolicy testPointPolicy = pointPolicyRepository.findById(pointPolicy.getPointPolicyId()).orElse(null);

        assertThat(testPointPolicy).isEqualTo(pointPolicy);
        assertThat(testPointPolicy.getPointPolicyId()).isEqualTo(pointPolicy.getPointPolicyId());
        assertThat(testPointPolicy.getPolicyName()).isEqualTo(pointPolicy.getPolicyName());
        assertThat(testPointPolicy.getPolicyType()).isEqualTo(pointPolicy.getPolicyType());
        assertThat(testPointPolicy.getChangedDate()).isEqualTo(pointPolicy.getChangedDate());
        assertThat(testPointPolicy.getAmount()).isEqualTo(pointPolicy.getAmount());
        assertThat(testPointPolicy.getIsActivated()).isTrue();
    }
}
