package com.t2m.g2nee.shop.bookset.book.repository;

import com.t2m.g2nee.shop.bookset.book.domain.Book;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface BookRepository extends JpaRepository<Book, Long>, BookCustomRepository {

    @Query("SELECT b FROM Book b WHERE b.bookStatus = 'DELETED'")
    Optional<Book> findByBookId(Long bookId);
}