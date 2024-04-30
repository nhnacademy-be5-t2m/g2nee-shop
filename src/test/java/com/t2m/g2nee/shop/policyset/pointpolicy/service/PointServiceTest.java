package com.t2m.g2nee.shop.policyset.pointpolicy.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertFalse;

import com.t2m.g2nee.shop.exception.AlreadyExistException;
import com.t2m.g2nee.shop.exception.NotFoundException;
import com.t2m.g2nee.shop.policyset.pointpolicy.domain.PointPolicy;
import com.t2m.g2nee.shop.policyset.pointpolicy.dto.request.PointPolicySaveDto;
import com.t2m.g2nee.shop.policyset.pointpolicy.dto.response.PointPolicyInfoDto;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;


@SpringBootTest
@Transactional
class PointServiceTest {

    @Autowired
    PointPolicyService pointPolicyService;

    PointPolicyInfoDto pointPolicy1;
    PointPolicyInfoDto pointPolicy2;
    PointPolicyInfoDto pointPolicy3;


    @BeforeEach
    void setUp() {
        pointPolicy1 = pointPolicyService.savePointPolicy(
                new PointPolicySaveDto("테스트 정책1", "PERCENT", BigDecimal.valueOf(0.25)));
        pointPolicy2 = pointPolicyService.savePointPolicy(
                new PointPolicySaveDto("테스트 정책2", "AMOUNT", BigDecimal.valueOf(1000)));
        pointPolicy3 = pointPolicyService.savePointPolicy(
                new PointPolicySaveDto("테스트 정책3", "PERCENT", BigDecimal.valueOf(0.1)));
    }

    @Test
    @DisplayName("포인트 정책 저장 테스트")
    void testSave() {
        assertNotNull(pointPolicy1.getPointPolicyId());
        assertNotNull(pointPolicy2.getPointPolicyId());
        assertNotNull(pointPolicy3.getPointPolicyId());
    }

    @Test
    @DisplayName("포인트 정책 저장 실패 테스트")
    void testSaveFail() {
        assertThrows(AlreadyExistException.class, () -> {
            pointPolicyService.savePointPolicy(
                    new PointPolicySaveDto("테스트 정책1", "PERCENT", BigDecimal.valueOf(0.25))
            );
        });
    }

    @Test
    @DisplayName("포인트 정책 수정 테스트")
    void testUpdate() {
        PointPolicySaveDto request = new PointPolicySaveDto("테스트 정책1", "AMOUNT", BigDecimal.valueOf(2000));
        pointPolicy1 = pointPolicyService.updatePointPolicy(pointPolicy1.getPointPolicyId(), request);

        assertEquals(request.getPolicyName(), pointPolicy1.getPolicyName());
        assertEquals(PointPolicy.PolicyType.valueOf(request.getPolicyType()).getName(), pointPolicy1.getPolicyType());
        assertEquals(request.getAmount(), new BigDecimal(pointPolicy1.getAmount()));
    }

    @Test
    @DisplayName("포인트 정책 수정 실패 테스트")
    void testUpdateFail() {
        assertThrows(NotFoundException.class, () -> {
            pointPolicyService.updatePointPolicy(1000000000L,
                    new PointPolicySaveDto("테스트 정책1", "AMOUNT", BigDecimal.valueOf(2000)));
        });
    }

    @Test
    @DisplayName("특정 포인트 정책 얻기 테스트")
    void testGetPointPolicy() {
        PointPolicyInfoDto testPointPolicy = pointPolicyService.getPointPolicy(pointPolicy1.getPointPolicyId());

        assertEquals(testPointPolicy.getPointPolicyId(), pointPolicy1.getPointPolicyId());
        assertEquals(testPointPolicy.getPolicyName(), pointPolicy1.getPolicyName());
        assertEquals(testPointPolicy.getPolicyType(), pointPolicy1.getPolicyType());
        assertEquals(testPointPolicy.getIsActivated(), pointPolicy1.getIsActivated());
        assertEquals(testPointPolicy.getChangedDate(), pointPolicy1.getChangedDate());
        assertEquals(testPointPolicy.getAmount(), pointPolicy1.getAmount());
    }

    @Test
    @DisplayName("특정 포인트 정책 얻기 실패 테스트")
    void testGetPointPolicyFail() {
        assertThrows(NotFoundException.class, () -> pointPolicyService.getPointPolicy(1000000000L));
    }

    @Test
    @DisplayName("모든 포인트 정책 얻기 테스트")
    void tesGetAllPointPolicy() {
        List<PointPolicyInfoDto> page = pointPolicyService.getAllPointPolicy(1).getData();

        assertThat(page).isNotNull().hasSizeGreaterThan(3);
    }

    @Test
    @DisplayName("포인트 정책 삭제 테스트")
    void testSoftDeletePointPolicy() {
        pointPolicyService.softDeletePointPolicy(pointPolicy1.getPointPolicyId());
        assertFalse(pointPolicyService.getPointPolicy(pointPolicy1.getPointPolicyId()).getIsActivated());
    }

    @Test
    @DisplayName("포인트 정책 삭제 실패 테스트")
    void testSoftDeletePointPolicyFail() {
        assertThrows(NotFoundException.class, () -> pointPolicyService.softDeletePointPolicy(1000000000L));

    }
}
