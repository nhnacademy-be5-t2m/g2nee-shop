package com.t2m.g2nee.shop.policyset.deliveryPolicy.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import com.t2m.g2nee.shop.config.ElasticsearchConfig;
import com.t2m.g2nee.shop.config.MapperConfig;
import com.t2m.g2nee.shop.policyset.deliveryPolicy.domain.DeliveryPolicy;
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
class DeliveryRepositoryTest {

    @Autowired
    DeliveryPolicyRepository deliveryPolicyRepository;
    DeliveryPolicy deliveryPolicy1;
    DeliveryPolicy deliveryPolicy2;
    DeliveryPolicy deliveryPolicy3;


    @BeforeEach
    void setUp() {
        deliveryPolicy1 =
                deliveryPolicyRepository.save(new DeliveryPolicy(BigDecimal.valueOf(2500), BigDecimal.valueOf(20000)));
        deliveryPolicy2 =
                deliveryPolicyRepository.save(new DeliveryPolicy(BigDecimal.valueOf(3000), BigDecimal.valueOf(20000)));
        deliveryPolicyRepository.softDelete();
        deliveryPolicy3 =
                deliveryPolicyRepository.save(new DeliveryPolicy(BigDecimal.valueOf(3500), BigDecimal.valueOf(50000)));
    }

    @Test
    void testCount() {
        assertEquals(3, deliveryPolicyRepository.count());
    }

    @Test
    void testSave() {
        assertNotNull(deliveryPolicyRepository.findById(deliveryPolicy1.getDeliveryPolicyId()));
        assertNotNull(deliveryPolicyRepository.findById(deliveryPolicy2.getDeliveryPolicyId()));
        assertNotNull(deliveryPolicyRepository.findById(deliveryPolicy3.getDeliveryPolicyId()));
    }

    @Test
    void testFindFirstByIsActivatedOrderByChangedDateDesc() {
        DeliveryPolicy recentPolicy =
                deliveryPolicyRepository.findFirstByIsActivatedOrderByChangedDateDesc(true).orElse(null);
        assertEquals(recentPolicy.getDeliveryPolicyId(), deliveryPolicy3.getDeliveryPolicyId());
        assertEquals(recentPolicy.getDeliveryFee(), deliveryPolicy3.getDeliveryFee());
        assertEquals(recentPolicy.getFreeDeliveryStandard(), deliveryPolicy3.getFreeDeliveryStandard());
        assertEquals(recentPolicy.getIsActivated(), deliveryPolicy3.getIsActivated());
        assertEquals(recentPolicy.getChangedDate(), deliveryPolicy3.getChangedDate());
    }

    @Test
    void testFindAll() {
        List<DeliveryPolicy> deliveryPolicies = deliveryPolicyRepository.findAll(
                PageRequest.of(0, 10, Sort.by("isActivated").descending()
                        .and(Sort.by("changedDate")))
        ).getContent();

        assertThat(deliveryPolicies).isNotNull();
        assertEquals(deliveryPolicies.get(0).getDeliveryPolicyId(), deliveryPolicy3.getDeliveryPolicyId());
        assertEquals(deliveryPolicies.get(1).getDeliveryPolicyId(), deliveryPolicy1.getDeliveryPolicyId());
        assertEquals(deliveryPolicies.get(2).getDeliveryPolicyId(), deliveryPolicy2.getDeliveryPolicyId());

    }

    @Test
    void testSoftDelete() {
        deliveryPolicyRepository.softDelete();
        assertFalse(
                deliveryPolicyRepository.findById(deliveryPolicy1.getDeliveryPolicyId()).orElse(null).getIsActivated());
        assertFalse(
                deliveryPolicyRepository.findById(deliveryPolicy2.getDeliveryPolicyId()).orElse(null).getIsActivated());
        assertFalse(
                deliveryPolicyRepository.findById(deliveryPolicy3.getDeliveryPolicyId()).orElse(null).getIsActivated());

    }
}
