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

/**
 * DeliveryPolicies의 Entity 입니다.
 *
 * @author : 김수빈
 * @since : 1.0
 */
@Entity
@Table(name = "DeliveryPolicies")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryPolicy {

    /**
     * 배송비 정책 id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long deliveryPolicyId;

    /**
     * 배송비
     */
    private BigDecimal deliveryFee;
    /**
     * 무료 배송 기준
     */
    private BigDecimal freeDeliveryStandard;

    /**
     * 정책 활성화 여부
     */
    private Boolean isActivated;
    /**
     * 정책 변경 날짜
     */
    private LocalDateTime changedDate;

    public DeliveryPolicy(BigDecimal deliveryFee, BigDecimal freeDeliveryStandard) {
        this.deliveryFee = deliveryFee;
        this.freeDeliveryStandard = freeDeliveryStandard;
        this.isActivated = true;
        this.changedDate = LocalDateTime.now();
    }
}
