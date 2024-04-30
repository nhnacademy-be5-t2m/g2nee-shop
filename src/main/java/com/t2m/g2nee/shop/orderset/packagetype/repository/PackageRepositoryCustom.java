package com.t2m.g2nee.shop.orderset.packagetype.repository;

/**
 * 포장지에 관련된 복잡한 쿼리를 QueryDSL로 작성하기 위한 인터페이스입니다.
 *
 * @author : 김수빈
 * @since : 1.0
 */
public interface PackageRepositoryCustom {

    /**
     * 포장지를 soft delete합니다.
     *
     * @param packageId 포장지 id
     */
    void softDeleteByPackageId(Long packageId);

    /**
     * 포장지를 활성화합니다.
     *
     * @param packageId 포장지 id
     */
    void activateByPackageId(Long packageId);

    /**
     * 포장지가 존재하면서 활성/비활성인지 확인합니다.
     *
     * @param packageId 포장지 id
     * @param active    true일 경우 활성인지 확인, false인 경우 비활성인지 확인
     * @return boolean
     */
    boolean getExistsByPackageIdAndIsActivated(Long packageId, boolean active);
}
