package com.t2m.g2nee.shop.review.repository;

import com.t2m.g2nee.shop.fileset.bookfile.domain.BookFile;
import com.t2m.g2nee.shop.fileset.reviewfile.domain.ReviewFile;
import com.t2m.g2nee.shop.review.domain.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface ReviewRepository extends JpaRepository<Review,Long>, ReviewCustomRepository{

}
