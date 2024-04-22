package com.t2m.g2nee.shop.policyset.pointPolicy.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * PointPolicies에 대한 Entity 입니다.
 *
 * @author : 김수빈
 * @since : 1.0
 */
@Entity
@Table(name = "PointPolicies")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PointPolicy {

    /**
     * 포인트 정책 id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pointPolicyId;
    /**
     * 포인트 정책 이름
     */
    private String policyName;
    /**
     * 포인트 정책 타입: 퍼센트 적립, 금액 적립
     */
    @Enumerated(EnumType.STRING)
    private PolicyType policyType;
    /**
     * 적립 금액
     */
    private BigDecimal amount;
    /**
     * 포인트 정책 활성화 유무
     */
    private Boolean isActivated;
    /**
     * 정책 수정 날짜
     */
    private LocalDateTime changedDate;

    public PointPolicy(String policyName, String policyType, BigDecimal amount) {
        this.policyName = policyName;
        this.policyType = PolicyType.valueOf(policyType);
        this.amount = amount;
        this.isActivated = true;
        this.changedDate = LocalDateTime.now();
    }

    public enum PolicyType {
        AMOUNT("금액적립"), PERCENT("퍼센트적립");

        private final String name;

        PolicyType(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }
}
