package com.t2m.g2nee.shop.bookset.bookcontributor.repository;

import com.t2m.g2nee.shop.bookset.bookcontributor.domain.BookContributor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;


/**
 * 책 기여자에 대한 Repository 입니다.
 *
 * @author : 신동민
 * @since : 1.0
 */
public interface BookContributorRepository extends JpaRepository<BookContributor, Long> {

    @Modifying
    @Query("DELETE FROM BookContributor bc WHERE bc.book.bookId = :bookId")
    void deleteByBookId(Long bookId);

}
