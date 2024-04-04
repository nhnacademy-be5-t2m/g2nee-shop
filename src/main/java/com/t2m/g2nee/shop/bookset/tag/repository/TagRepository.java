package com.t2m.g2nee.shop.bookset.tag.repository;

import com.t2m.g2nee.shop.bookset.tag.domain.Tag;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TagRepository extends JpaRepository<Tag, Long> {

    Optional<Tag> findByTagName(String tagName);



}
