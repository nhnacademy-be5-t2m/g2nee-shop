package com.t2m.g2nee.shop.bookset.tag.repository;

import com.t2m.g2nee.shop.bookset.tag.domain.Tag;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface TagRepository extends JpaRepository<Tag, Long> {

    Optional<Tag> findByTagName(String tagName);

    @Query("SELECT t FROM Tag t WHERE t.isActivated=true")
    Page<Tag> findAllActivated(Pageable pageable);

    @Query("SELECT t FROM Tag t WHERE t.isActivated=true ORDER BY t.tagName")
    List<Tag> findAllActivated();


}
