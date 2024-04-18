package com.t2m.g2nee.shop.policyset.pointPolicy.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import com.t2m.g2nee.shop.config.ElasticsearchConfig;
import com.t2m.g2nee.shop.config.MapperConfig;
import com.t2m.g2nee.shop.policyset.pointPolicy.domain.PointPolicy;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

@DataJpaTest
@ActiveProfiles("test")
@TestPropertySource(locations = "classpath:application-test.properties")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(value = {MapperConfig.class, ElasticsearchConfig.class})
class PointRepositoryTest {

    @Autowired
    PointPolicyRepository pointPolicyRepository;

    PointPolicy pointPolicy1;
    PointPolicy pointPolicy2;
    PointPolicy pointPolicy3;


    @BeforeEach
    void setUp() {
        pointPolicy1 = pointPolicyRepository.save(new PointPolicy("테스트 정책1", "PERCENT", BigDecimal.valueOf(0.25)));
        pointPolicy2 = pointPolicyRepository.save(new PointPolicy("테스트 정책2", "AMOUNT", BigDecimal.valueOf(1000)));
        pointPolicy3 = pointPolicyRepository.save(new PointPolicy("테스트 정책3", "PERCENT", BigDecimal.valueOf(0.1)));
        pointPolicyRepository.softDelete(pointPolicy2.getPointPolicyId());
    }

    @Test
    void testExistsById() {
        assertTrue(pointPolicyRepository.existsById(pointPolicy1.getPointPolicyId()));
        assertTrue(pointPolicyRepository.existsById(pointPolicy2.getPointPolicyId()));
        assertTrue(pointPolicyRepository.existsById(pointPolicy3.getPointPolicyId()));
    }

    @Test
    void testSave() {
        assertNotNull(pointPolicyRepository.findById(pointPolicy1.getPointPolicyId()));
        assertNotNull(pointPolicyRepository.findById(pointPolicy2.getPointPolicyId()));
        assertNotNull(pointPolicyRepository.findById(pointPolicy3.getPointPolicyId()));
    }

    @Test
    void testExistsByPolicyNameAndIsActivated() {
        assertTrue(pointPolicyRepository.existsByPolicyNameAndIsActivated("테스트 정책1", true));
    }

    @Test
    void testFindAll() {
        List<PointPolicy> packageTypes = pointPolicyRepository.findAll(
                PageRequest.of(0, 10, Sort.by("isActivated").descending())).getContent();

        assertThat(packageTypes).isNotNull();
        assertEquals(packageTypes.get(0).getPointPolicyId(), pointPolicy1.getPointPolicyId());
        assertEquals(packageTypes.get(2).getPointPolicyId(), pointPolicy3.getPointPolicyId());
        assertEquals(packageTypes.get(1).getPointPolicyId(), pointPolicy2.getPointPolicyId());

    }

    @Test
    void testSoftDelete() {
        pointPolicyRepository.softDelete(pointPolicy1.getPointPolicyId());
        assertFalse(pointPolicyRepository.findById(pointPolicy1.getPointPolicyId()).orElse(null).getIsActivated());
    }
}
