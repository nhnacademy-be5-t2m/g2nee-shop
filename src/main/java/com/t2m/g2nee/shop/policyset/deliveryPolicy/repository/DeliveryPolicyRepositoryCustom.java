package com.t2m.g2nee.shop.policyset.deliveryPolicy.repository;

/**
 * QueryDSL을 사용하여 복잡한 쿼리를 처리하기 위한 인터페이스입니다.
 *
 * @author : 김수빈
 * @since : 1.0
 */
public interface DeliveryPolicyRepositoryCustom {

    /**
     * 정책을 soft delete합니다.
     */
    void softDelete();
}
