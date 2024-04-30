package com.t2m.g2nee.shop.orderset.packagetype.repository.impl;

import com.t2m.g2nee.shop.orderset.packagetype.domain.PackageType;
import com.t2m.g2nee.shop.orderset.packagetype.domain.QPackageType;
import com.t2m.g2nee.shop.orderset.packagetype.repository.PackageRepositoryCustom;
import javax.persistence.EntityManager;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

/**
 * PackageRepositoryCustom의 구현체입니다.
 *
 * @author : 김수빈
 * @since : 1.0
 */
public class PackageRepositoryCustomImpl extends QuerydslRepositorySupport implements PackageRepositoryCustom {

    /**
     * 영속성 컨텍스트를 관리하기 위한 EntityManager입니다.
     * update 쿼리 사용시, 영속성 업데이트를 위해 사용합니다.
     */
    private final EntityManager entityManager;

    /**
     * PackageRepositoryCustomImpl의 생성자입니다.
     *
     * @param entityManager 엔티티 매니저
     */
    public PackageRepositoryCustomImpl(EntityManager entityManager) {
        super(PackageType.class);
        this.entityManager = entityManager;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void softDeleteByPackageId(Long packageId) {
        QPackageType packageType = QPackageType.packageType;

        /**
         * UPDATE PackageTypes p SET c.isActivated = false WHERE p.packageId = ?;
         */
        update(packageType)
                .set(packageType.isActivated, false)
                .where(packageType.packageId.eq(packageId)).execute();

        entityManager.clear();
        entityManager.flush();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void activateByPackageId(Long packageId) {
        QPackageType packageType = QPackageType.packageType;

        /**
         * UPDATE PackageTypes p SET c.isActivated = true WHERE p.packageId = ?;
         */
        update(packageType)
                .set(packageType.isActivated, true)
                .where(packageType.packageId.eq(packageId)).execute();

        entityManager.clear();
        entityManager.flush();

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean getExistsByPackageIdAndIsActivated(Long packageId, boolean active) {
        QPackageType packageType = QPackageType.packageType;

        /**
         * SELECT CASE WHEN COUNT(packageId) > 0 THEN true ELSE false END
         * FROM PackageTypes p
         * WHERE p.packageId = 1
         * AND p.isActivated is false;
         */
        if (active) {
            return from(packageType)
                    .where(packageType.packageId.eq(packageId)
                            .and(packageType.isActivated.isTrue()))
                    .select(packageType.packageId.count().gt(0)).fetchOne();
        } else {
            return from(packageType)
                    .where(packageType.packageId.eq(packageId)
                            .and(packageType.isActivated.isFalse()))
                    .select(packageType.packageId.count().gt(0)).fetchOne();
        }
    }
}