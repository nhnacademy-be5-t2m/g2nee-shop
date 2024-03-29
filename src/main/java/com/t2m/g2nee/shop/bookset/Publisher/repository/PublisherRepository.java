package com.t2m.g2nee.shop.bookset.Publisher.repository;

import com.t2m.g2nee.shop.bookset.Publisher.domain.Publisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PublisherRepository extends JpaRepository<Publisher, Long> {
    Page<Publisher> findAll(Pageable pageable);
}
