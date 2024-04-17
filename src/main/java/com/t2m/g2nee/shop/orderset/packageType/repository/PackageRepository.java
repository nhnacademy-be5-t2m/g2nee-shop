package com.t2m.g2nee.shop.orderset.packageType.repository;

import com.t2m.g2nee.shop.orderset.packageType.domain.PackageType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PackageRepository extends JpaRepository<PackageType, Long>, PackageRepositoryCustom {

    boolean existsByName(String name);

    Page<PackageType> findAll(Pageable pageable);
}
