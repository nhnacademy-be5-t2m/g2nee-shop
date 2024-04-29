package com.t2m.g2nee.shop.policyset.deliverypolicy.repository.impl;

import com.t2m.g2nee.shop.policyset.deliverypolicy.domain.DeliveryPolicy;
import com.t2m.g2nee.shop.policyset.deliverypolicy.domain.QDeliveryPolicy;
import com.t2m.g2nee.shop.policyset.deliverypolicy.repository.DeliveryPolicyRepositoryCustom;
import javax.persistence.EntityManager;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

/**
 * DeliveryPolicyRepositoryCustom의 구현체입니다.
 *
 * @author : 김수빈
 * @since : 1.0
 */
public class DeliveryPolicyRepositoryCustomImpl extends QuerydslRepositorySupport implements
        DeliveryPolicyRepositoryCustom {

    /**
     * 영속성 컨텍스트를 관리하기 위한 EntityManager입니다.
     * update 쿼리 사용시, 영속성 업데이트를 위해 사용합니다.
     */
    private final EntityManager entityManager;

    /**
     * DeliveryPolicyRepositoryCustomImpl의 생성자입니다.
     * @param entityManager 엔티티 매니저
     */
    public DeliveryPolicyRepositoryCustomImpl(EntityManager entityManager) {
        super(DeliveryPolicy.class);
        this.entityManager = entityManager;
    }

    /**
     * {@inheritDoc}
     */
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