package com.t2m.g2nee.shop.like.repository;

import com.t2m.g2nee.shop.like.domain.BookLike;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BookLikeRepository extends JpaRepository<BookLike, Long> {
    @Query("SELECT bl FROM BookLike bl WHERE bl.book.bookId = :bookId AND bl.member.customerId = :memberId")
    Optional<BookLike> findBookLike(Long memberId, Long bookId);
}
