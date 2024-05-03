package com.t2m.g2nee.shop.fileset.packagefile.repository;

import com.t2m.g2nee.shop.fileset.packagefile.domain.PackageFile;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PackageFileRepository extends JpaRepository<PackageFile, Long> {

    Optional<PackageFile> findByPackageType_PackageId(Long packageId);

}
