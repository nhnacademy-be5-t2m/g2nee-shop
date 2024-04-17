package com.t2m.g2nee.shop.policyset.pointPolicy.repository.impl;

import com.t2m.g2nee.shop.policyset.pointPolicy.domain.PointPolicy;
import com.t2m.g2nee.shop.policyset.pointPolicy.domain.QPointPolicy;
import com.t2m.g2nee.shop.policyset.pointPolicy.repository.PointPolicyRepositoryCustom;
import javax.persistence.EntityManager;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

public class PointPolicyRepositoryCustomImpl extends QuerydslRepositorySupport implements
        PointPolicyRepositoryCustom {

    private final EntityManager entityManager;

    public PointPolicyRepositoryCustomImpl(EntityManager entityManager) {
        super(PointPolicy.class);
        this.entityManager = entityManager;
    }

    @Override
    public void softDelete() {
        QPointPolicy pointPolicy = QPointPolicy.pointPolicy;

        /**
         * UPDATE `PointPolicy` SET `isActivated` = false;
         */

        update(pointPolicy)
                .set(pointPolicy.isActivated, false)
                .execute();

        entityManager.clear();
        entityManager.flush();
    }
}