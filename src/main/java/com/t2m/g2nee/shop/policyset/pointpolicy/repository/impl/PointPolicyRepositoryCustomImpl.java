package com.t2m.g2nee.shop.policyset.pointpolicy.repository.impl;

import com.t2m.g2nee.shop.policyset.pointpolicy.domain.PointPolicy;
import com.t2m.g2nee.shop.policyset.pointpolicy.domain.QPointPolicy;
import com.t2m.g2nee.shop.policyset.pointpolicy.repository.PointPolicyRepositoryCustom;
import javax.persistence.EntityManager;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

/**
 * PointPolicyRepositoryCustom의 구현체입니다.
 *
 * @author : 김수빈
 * @since : 1.0
 */
public class PointPolicyRepositoryCustomImpl extends QuerydslRepositorySupport implements
        PointPolicyRepositoryCustom {

    /**
     * 영속성 컨텍스트를 관리하기 위한 EntityManager입니다.
     * update 쿼리 사용시, 영속성 업데이트를 위해 사용합니다.
     */
    private final EntityManager entityManager;

    /**
     * PointPolicyRepositoryCustomImpl의 생성자입니다.
     * @param entityManager 엔티티 매니저
     */
    public PointPolicyRepositoryCustomImpl(EntityManager entityManager) {
        super(PointPolicy.class);
        this.entityManager = entityManager;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void softDelete(Long pointPolicyId) {
        QPointPolicy pointPolicy = QPointPolicy.pointPolicy;

        /**
         * UPDATE `PointPolicy` SET `isActivated` = false;
         */

        update(pointPolicy)
                .set(pointPolicy.isActivated, false)
                .where(pointPolicy.pointPolicyId.eq(pointPolicyId))
                .execute();

        entityManager.clear();
        entityManager.flush();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean getExistsByPointPolicyIdAndIsActivated(Long pointPolicyId, boolean active) {
        QPointPolicy pointPolicy = QPointPolicy.pointPolicy;

        /**
         * SELECT CASE WHEN COUNT(packageId) > 0 THEN true ELSE false END
         * FROM PointPolicies p
         * WHERE p.pointPolicyId = ?
         * AND p.isActivated is false;
         */
        if (active) {
            return from(pointPolicy)
                    .where(pointPolicy.pointPolicyId.eq(pointPolicyId)
                            .and(pointPolicy.isActivated.isTrue()))
                    .select(pointPolicy.pointPolicyId.count().gt(0)).fetchOne();
        } else {
            return from(pointPolicy)
                    .where(pointPolicy.pointPolicyId.eq(pointPolicyId)
                            .and(pointPolicy.isActivated.isFalse()))
                    .select(pointPolicy.pointPolicyId.count().gt(0)).fetchOne();
        }
    }
}