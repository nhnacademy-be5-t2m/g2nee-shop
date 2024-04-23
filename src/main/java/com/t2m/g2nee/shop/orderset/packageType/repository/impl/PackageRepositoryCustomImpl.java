package com.t2m.g2nee.shop.orderset.packageType.repository.impl;

import com.t2m.g2nee.shop.orderset.packageType.domain.PackageType;
import com.t2m.g2nee.shop.orderset.packageType.domain.QPackageType;
import com.t2m.g2nee.shop.orderset.packageType.repository.PackageRepositoryCustom;
import javax.persistence.EntityManager;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

/**
 * PackageRepositoryCustom의 구현체입니다.
 *
 * @author : 김수빈
 * @since : 1.0
 */
public class PackageRepositoryCustomImpl extends QuerydslRepositorySupport implements PackageRepositoryCustom {

    private final EntityManager entityManager;

    public PackageRepositoryCustomImpl(EntityManager entityManager) {
        super(PackageType.class);
        this.entityManager = entityManager;
    }

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