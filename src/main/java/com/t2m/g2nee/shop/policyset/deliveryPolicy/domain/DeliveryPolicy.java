package com.t2m.g2nee.shop.policyset.deliveryPolicy.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "DeliveryPolicies")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryPolicy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long deliveryPolicyId;

    private BigDecimal deliveryFee;
    private BigDecimal freeDeliveryStandard;

    private Boolean isActivated;
    private LocalDateTime changedDate;

    public DeliveryPolicy(BigDecimal deliveryFee, BigDecimal freeDeliveryStandard) {
        this.deliveryFee = deliveryFee;
        this.freeDeliveryStandard = freeDeliveryStandard;
        this.isActivated = true;
        this.changedDate = LocalDateTime.now();
    }
}
