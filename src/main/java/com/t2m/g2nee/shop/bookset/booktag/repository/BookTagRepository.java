package com.t2m.g2nee.shop.bookset.booktag.repository;

import com.t2m.g2nee.shop.bookset.booktag.domain.BookTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface BookTagRepository extends JpaRepository<BookTag, Long> {

    @Modifying
    @Query("DELETE FROM BookTag bt WHERE bt.book.bookId =:bookId")
    void deleteByBookId(Long bookId);
}
