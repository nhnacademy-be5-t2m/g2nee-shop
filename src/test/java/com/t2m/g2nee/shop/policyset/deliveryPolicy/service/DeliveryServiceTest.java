package com.t2m.g2nee.shop.policyset.deliveryPolicy.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.t2m.g2nee.shop.policyset.deliveryPolicy.dto.request.DeliveryPolicySaveDto;
import com.t2m.g2nee.shop.policyset.deliveryPolicy.dto.response.DeliveryPolicyInfoDto;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;


@SpringBootTest
@Transactional
class DeliveryServiceTest {

    @Autowired
    DeliveryPolicyService deliveryPolicyService;

    DeliveryPolicyInfoDto deliveryPolicy1;
    DeliveryPolicyInfoDto deliveryPolicy2;
    DeliveryPolicyInfoDto deliveryPolicy3;


    @BeforeEach
    void setUp() {
        deliveryPolicy1 = deliveryPolicyService.saveDeliveryPolicy(new DeliveryPolicySaveDto(2500, 10000));
        deliveryPolicy2 = deliveryPolicyService.saveDeliveryPolicy(new DeliveryPolicySaveDto(3000, 20000));
        deliveryPolicy3 = deliveryPolicyService.saveDeliveryPolicy(new DeliveryPolicySaveDto(3500, 50000));

    }

    @Test
    void testSave() {
        assertNotNull(deliveryPolicy1.getDeliveryPolicyId());
        assertNotNull(deliveryPolicy2.getDeliveryPolicyId());
        assertNotNull(deliveryPolicy3.getDeliveryPolicyId());
    }

    @Test
    void testGetDeliveryPolicy() {
        DeliveryPolicyInfoDto testDeliveryPolicy = deliveryPolicyService.getDeliveryPolicy();

        assertEquals(testDeliveryPolicy.getDeliveryPolicyId(), deliveryPolicy3.getDeliveryPolicyId());
        assertEquals(testDeliveryPolicy.getDeliveryFee(), deliveryPolicy3.getDeliveryFee());
        assertEquals(testDeliveryPolicy.getFreeDeliveryStandard(), deliveryPolicy3.getFreeDeliveryStandard());
        assertEquals(testDeliveryPolicy.getIsActivated(), deliveryPolicy3.getIsActivated());
        assertEquals(testDeliveryPolicy.getChangedDate(), deliveryPolicy3.getChangedDate());
    }

    @Test
    void tesGetAllDeliveryPolicy() {
        List<DeliveryPolicyInfoDto> page = deliveryPolicyService.getAllDeliveryPolicy(1).getData();

        assertThat(page).isNotNull().hasSizeGreaterThanOrEqualTo(3);
    }
}