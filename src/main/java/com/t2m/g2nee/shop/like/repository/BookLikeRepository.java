package com.t2m.g2nee.shop.like.repository;

import com.t2m.g2nee.shop.like.domain.BookLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookLikeRepository extends JpaRepository<BookLike,Long> {
}
