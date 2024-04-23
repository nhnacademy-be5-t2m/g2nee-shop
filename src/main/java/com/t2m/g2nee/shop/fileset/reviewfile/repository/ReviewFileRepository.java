package com.t2m.g2nee.shop.fileset.reviewfile.repository;

import com.t2m.g2nee.shop.fileset.reviewfile.domain.ReviewFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface ReviewFileRepository extends JpaRepository<ReviewFile, Long> {

    @Modifying
    @Query("DELETE FROM ReviewFile rf WHERE rf.review.reviewId = :reviewId")
    void deleteByReviewId(Long reviewId);


    @Query("SELECT rf FROM ReviewFile rf WHERE rf.review.reviewId = :reviewId")
    ReviewFile findByReviewId(Long reviewId);
}
