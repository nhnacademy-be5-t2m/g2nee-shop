package com.t2m.g2nee.shop.policyset.DeliveryPolicy.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "DeliveryPolicies")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryPolicy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long deliveryPolicyId;

    private int deliveryFee;
    private int freeDeliveryStandard;
}
