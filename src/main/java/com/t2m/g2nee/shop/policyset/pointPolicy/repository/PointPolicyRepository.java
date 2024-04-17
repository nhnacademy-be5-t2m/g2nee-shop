package com.t2m.g2nee.shop.policyset.pointPolicy.repository;

import com.t2m.g2nee.shop.policyset.deliveryPolicy.domain.DeliveryPolicy;
import com.t2m.g2nee.shop.policyset.pointPolicy.domain.PointPolicy;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PointPolicyRepository extends JpaRepository<PointPolicy, Long>, PointPolicyRepositoryCustom {

    boolean existsByPolicyNameAndIsActivated(String policyName, boolean isActivated);

    Page<PointPolicy> findAll(Pageable pageable);

    Optional<DeliveryPolicy> findFirstByIsActivatedOrderByIsActivatedDesc(boolean isActivated);
}
