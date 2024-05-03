package com.t2m.g2nee.shop.policyset.pointpolicy.repository;

/**
 * QueryDSL을 사용하여 복잡한 쿼리를 작성합니다.
 *
 * @author : 김수빈
 * @since : 1.0
 */
public interface PointPolicyRepositoryCustom {

    /**
     * 해당하는 정책을 soft delete합니다.
     *
     * @param pointPolicyId 포인트 정책 id
     */
    void softDelete(Long pointPolicyId);

    /**
     * 해당 포인트 정책이 존재하면서 활성화/비활성화인지 확인합니다.
     *
     * @param pointPolicyId 포인트 정책 id
     * @param active        true시 활성화인지 확인, false시 비활성화인지 확인
     * @return boolean
     */
    boolean getExistsByPointPolicyIdAndIsActivated(Long pointPolicyId, boolean active);
}
