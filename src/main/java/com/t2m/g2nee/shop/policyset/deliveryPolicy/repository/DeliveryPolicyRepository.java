package com.t2m.g2nee.shop.policyset.deliveryPolicy.repository;

import com.t2m.g2nee.shop.policyset.deliveryPolicy.domain.DeliveryPolicy;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 배송비 정책 엔티티의 데이터베이스 테이블에 접근하는 메소드를 사용하기 위한 인터페이스입니다.
 *
 * @author : 김수빈
 * @since : 1.0
 */
public interface DeliveryPolicyRepository extends JpaRepository<DeliveryPolicy, Long>, DeliveryPolicyRepositoryCustom {

    /**
     * 모든 배송비 정책을 페이징 처리하여 반환합니다.
     * @param pageable 페이징 객체
     * @return Page<DeliveryPolicy>
     */
    Page<DeliveryPolicy> findAll(Pageable pageable);

    /**
     * 현제 활성중인 배송비 정책을 가져옵니다.
     * @param isActivated 정책 활성화 여부
     * @return Optional<DeliveryPolicy>
     */
    Optional<DeliveryPolicy> findFirstByIsActivatedOrderByChangedDateDesc(boolean isActivated);
}
