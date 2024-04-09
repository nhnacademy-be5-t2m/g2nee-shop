package com.t2m.g2nee.shop.fileset.bookfile.repostitory;

import com.t2m.g2nee.shop.fileset.bookfile.domain.BookFile;
import static com.t2m.g2nee.shop.fileset.bookfile.domain.BookFile.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface BookFileRepository extends JpaRepository<BookFile, Long> {

    @Modifying
    @Query("DELETE FROM BookFile bf WHERE bf.book.bookId = :bookId AND bf.imageType = :imageType")
    void deleteByBookIdAndImageType(Long bookId, ImageType imageType);


    @Query("SELECT bf FROM BookFile bf WHERE bf.book.bookId = :bookId AND bf.imageType = :imageType")
    BookFile findByBookIdAndImageType(Long bookId, ImageType imageType);
}