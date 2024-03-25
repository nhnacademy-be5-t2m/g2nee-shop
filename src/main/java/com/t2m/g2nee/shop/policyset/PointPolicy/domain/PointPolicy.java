package com.t2m.g2nee.shop.policyset.PointPolicy.domain;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

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
    private String policyType;
    private BigDecimal amount;
}
