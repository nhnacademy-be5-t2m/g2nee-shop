package com.t2m.g2nee.shop.policyset.deliveryPolicy.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.t2m.g2nee.shop.config.ElasticsearchConfig;
import com.t2m.g2nee.shop.config.MapperConfig;
import com.t2m.g2nee.shop.policyset.deliveryPolicy.repository.DeliveryPolicyRepository;
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
public class DeliveryPolicyTest {

    @Autowired
    DeliveryPolicyRepository deliveryPolicyRepository;

    @Test
    void test() {
        DeliveryPolicy deliveryPolicy = new DeliveryPolicy(BigDecimal.valueOf(3000), BigDecimal.valueOf(30000));

        deliveryPolicyRepository.saveAndFlush(deliveryPolicy);

        DeliveryPolicy testDeliveryPolicy =
                deliveryPolicyRepository.findById(deliveryPolicy.getDeliveryPolicyId()).orElse(null);

        assertThat(testDeliveryPolicy).isEqualTo(deliveryPolicy);
        assertThat(testDeliveryPolicy.getDeliveryPolicyId()).isEqualTo(deliveryPolicy.getDeliveryPolicyId());
        assertThat(testDeliveryPolicy.getDeliveryFee()).isEqualTo(deliveryPolicy.getDeliveryFee());
        assertThat(testDeliveryPolicy.getFreeDeliveryStandard()).isEqualTo(deliveryPolicy.getFreeDeliveryStandard());
        assertThat(testDeliveryPolicy.getChangedDate()).isEqualTo(deliveryPolicy.getChangedDate());
        assertThat(testDeliveryPolicy.getIsActivated()).isTrue();
    }
}
