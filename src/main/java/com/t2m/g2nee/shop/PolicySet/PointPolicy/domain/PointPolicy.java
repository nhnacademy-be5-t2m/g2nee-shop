package com.t2m.g2nee.shop.PolicySet.PointPolicy.domain;

import lombok.*;

import javax.persistence.*;

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
    private String policyType;
    private long amount;
}
