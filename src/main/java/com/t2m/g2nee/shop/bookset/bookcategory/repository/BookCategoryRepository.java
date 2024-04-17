package com.t2m.g2nee.shop.bookset.bookcategory.repository;

import com.t2m.g2nee.shop.bookset.bookcategory.domain.BookCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface BookCategoryRepository extends JpaRepository<BookCategory, Long> {

    @Modifying
    @Query("DELETE FROM BookCategory bc WHERE bc.book.bookId = :bookId")
    void deleteByBookId(Long bookId);

}
