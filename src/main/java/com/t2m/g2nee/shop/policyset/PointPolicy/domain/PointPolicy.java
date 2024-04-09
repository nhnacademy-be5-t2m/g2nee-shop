package com.t2m.g2nee.shop.policyset.PointPolicy.domain;

import java.math.BigDecimal;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "PointPolicies")
@Getter
@Setter
@Builder
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

    public enum PolicyType {
        AMOUNT, PERCENT
    }
}
