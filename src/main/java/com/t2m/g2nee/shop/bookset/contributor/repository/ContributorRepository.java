package com.t2m.g2nee.shop.bookset.contributor.repository;

import com.t2m.g2nee.shop.bookset.contributor.domain.Contributor;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContributorRepository extends JpaRepository<Contributor, Long> {

    Optional<Contributor> findByContributorName(String contributorName);
}
