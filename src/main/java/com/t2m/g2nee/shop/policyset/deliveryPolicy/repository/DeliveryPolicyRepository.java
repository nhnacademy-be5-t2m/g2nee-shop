package com.t2m.g2nee.shop.policyset.deliveryPolicy.repository;

import com.t2m.g2nee.shop.policyset.deliveryPolicy.domain.DeliveryPolicy;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeliveryPolicyRepository extends JpaRepository<DeliveryPolicy, Long>, DeliveryPolicyRepositoryCustom {

    Page<DeliveryPolicy> findAll(Pageable pageable);

    Optional<DeliveryPolicy> findFirstByIsActivatedOrderByChangedDateDesc(boolean isActivated);
}
