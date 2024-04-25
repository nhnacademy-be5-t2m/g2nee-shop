package com.t2m.g2nee.shop.policyset.pointPolicy.repository;

import com.t2m.g2nee.shop.policyset.pointPolicy.domain.PointPolicy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 포인트 정책 엔티티의 데이터베이스 테이블에 접근하는 메소드를 사용하기 위한 인터페이스입니다.
 *
 * @author : 김수빈
 * @since : 1.0
 */
public interface PointPolicyRepository extends JpaRepository<PointPolicy, Long>, PointPolicyRepositoryCustom {

    /**
     * 포인트 정책의 이름이 같으면서 활성화 되어 있는지 확인하는 메소드입니다.
     *
     * @param policyName  포인트 정책 이름
     * @param isActivated 활성화 여부
     * @return boolean
     */
    boolean existsByPolicyNameAndIsActivated(String policyName, boolean isActivated);

    /**
     * 활성화된 포인트 정책의 이름이 같으면서 특정 id가 아닌 정책이 있는지 확인하는 메소드입니다.
     *
     * @param name
     * @param id
     * @return
     */
    boolean existsByPolicyNameAndPointPolicyIdNotAndIsActivated(String name, Long id, boolean isActivated);

    /**
     * 모든 포인트 정책을 페이징처리하여 반환합니다.
     *
     * @param pageable 페이징 객체
     * @return Page<PointPolicy>
     */
    Page<PointPolicy> findAll(Pageable pageable);
}
