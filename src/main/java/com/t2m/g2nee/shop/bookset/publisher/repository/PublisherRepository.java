package com.t2m.g2nee.shop.bookset.publisher.repository;

import com.t2m.g2nee.shop.bookset.publisher.domain.Publisher;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PublisherRepository extends JpaRepository<Publisher, Long> {

    Optional<Publisher> findByPublisherName(String publisherName);

    @Query("SELECT p FROM Publisher p WHERE p.isActivated=true")
    Page<Publisher> findAllActivated(Pageable pageable);

    @Query("SELECT p FROM Publisher p WHERE p.isActivated=true ORDER BY p.publisherName")
    List<Publisher> findAllActivated();

}
