package com.t2m.g2nee.shop.bookset.Book.repository;

import com.t2m.g2nee.shop.bookset.Book.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}
