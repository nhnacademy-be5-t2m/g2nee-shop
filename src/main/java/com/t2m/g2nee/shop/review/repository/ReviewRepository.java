package com.t2m.g2nee.shop.review.repository;

import com.t2m.g2nee.shop.review.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 리뷰 repository
 *
 * @author : 박재희
 * @since : 1.0
 */
public interface ReviewRepository extends JpaRepository<Review, Long>, ReviewCustomRepository{
}
