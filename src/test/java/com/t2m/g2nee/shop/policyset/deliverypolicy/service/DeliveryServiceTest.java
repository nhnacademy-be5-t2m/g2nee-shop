package com.t2m.g2nee.shop.policyset.deliverypolicy.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.t2m.g2nee.shop.policyset.deliverypolicy.dto.request.DeliveryPolicySaveDto;
import com.t2m.g2nee.shop.policyset.deliverypolicy.dto.response.DeliveryPolicyInfoDto;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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
    @DisplayName("배송비 정책 저장 테스트")
    void testSave() {
        assertNotNull(deliveryPolicy1.getDeliveryPolicyId());
        assertNotNull(deliveryPolicy2.getDeliveryPolicyId());
        assertNotNull(deliveryPolicy3.getDeliveryPolicyId());
    }

    @Test
    @DisplayName("현재 배송비 정책 얻기 테스트")
    void testGetDeliveryPolicy() {
        DeliveryPolicyInfoDto testDeliveryPolicy = deliveryPolicyService.getDeliveryPolicy();

        assertEquals(testDeliveryPolicy.getDeliveryPolicyId(), deliveryPolicy3.getDeliveryPolicyId());
        assertEquals(testDeliveryPolicy.getDeliveryFee(), deliveryPolicy3.getDeliveryFee());
        assertEquals(testDeliveryPolicy.getFreeDeliveryStandard(), deliveryPolicy3.getFreeDeliveryStandard());
        assertEquals(testDeliveryPolicy.getIsActivated(), deliveryPolicy3.getIsActivated());
        assertEquals(testDeliveryPolicy.getChangedDate(), deliveryPolicy3.getChangedDate());
    }

    @Test
    @DisplayName("모든 배송비 정책 얻기 테스트")
    void tesGetAllDeliveryPolicy() {
        List<DeliveryPolicyInfoDto> page = deliveryPolicyService.getAllDeliveryPolicy(1).getData();

        assertThat(page).isNotNull().hasSizeGreaterThanOrEqualTo(3);
    }
}
