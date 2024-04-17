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

@Entity
@Table(name = "PointPolicies")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PointPolicy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pointPolicyId;
    private String policyName;
    @Enumerated(EnumType.STRING)
    private PolicyType policyType;
    private BigDecimal amount;
    private Boolean isActivated;
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
