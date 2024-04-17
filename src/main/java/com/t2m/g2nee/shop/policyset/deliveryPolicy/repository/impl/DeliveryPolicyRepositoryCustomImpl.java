package com.t2m.g2nee.shop.policyset.deliveryPolicy.repository.impl;

import com.t2m.g2nee.shop.policyset.deliveryPolicy.domain.DeliveryPolicy;
import com.t2m.g2nee.shop.policyset.deliveryPolicy.domain.QDeliveryPolicy;
import com.t2m.g2nee.shop.policyset.deliveryPolicy.repository.DeliveryPolicyRepositoryCustom;
import javax.persistence.EntityManager;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

public class DeliveryPolicyRepositoryCustomImpl extends QuerydslRepositorySupport implements
        DeliveryPolicyRepositoryCustom {

    private final EntityManager entityManager;

    public DeliveryPolicyRepositoryCustomImpl(EntityManager entityManager) {
        super(DeliveryPolicy.class);
        this.entityManager = entityManager;
    }

    @Override
    public void softDelete() {
        QDeliveryPolicy deliveryPolicy = QDeliveryPolicy.deliveryPolicy;

        /**
         * UPDATE `DeliveryPolicies` SET `isActivated` = false;
         */

        update(deliveryPolicy)
                .set(deliveryPolicy.isActivated, false)
                .execute();

        entityManager.clear();
        entityManager.flush();
    }
}