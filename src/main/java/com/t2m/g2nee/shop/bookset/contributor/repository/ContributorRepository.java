package com.t2m.g2nee.shop.bookset.contributor.repository;

import com.t2m.g2nee.shop.bookset.contributor.domain.Contributor;
import com.t2m.g2nee.shop.bookset.publisher.domain.Publisher;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ContributorRepository extends JpaRepository<Contributor, Long> {

    Optional<Contributor> findByContributorName(String contributorName);

    @Query("SELECT c FROM Contributor c WHERE c.isActivated=true")
    Page<Contributor> findAllActivated(Pageable pageable);

    @Query("SELECT c FROM Contributor c WHERE c.isActivated=true ORDER BY c.contributorName")
    List<Contributor> findAllActivated();

}
