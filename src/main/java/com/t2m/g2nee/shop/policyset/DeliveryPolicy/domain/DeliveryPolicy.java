package com.t2m.g2nee.shop.policyset.DeliveryPolicy.domain;

import javax.persistence.Entity;
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
