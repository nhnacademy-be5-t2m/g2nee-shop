package com.t2m.g2nee.shop.bookset.bookcategory.repository;

import com.t2m.g2nee.shop.bookset.bookcategory.domain.BookCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

public interface BookCategoryRepository extends JpaRepository<BookCategory, Long> {

}
