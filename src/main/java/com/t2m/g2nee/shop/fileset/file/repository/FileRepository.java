package com.t2m.g2nee.shop.fileset.file.repository;

import com.t2m.g2nee.shop.fileset.file.domain.File;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<File, Long> {
}
