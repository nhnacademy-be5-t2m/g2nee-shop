package com.t2m.g2nee.shop.orderset.packageType.repository;

public interface PackageRepositoryCustom {

    void softDeleteByPackageId(Long packageId);

    void activateByPackageId(Long packageId);

    boolean getExistsByPackageIdAndIsActivated(Long packageId, boolean active);
}
