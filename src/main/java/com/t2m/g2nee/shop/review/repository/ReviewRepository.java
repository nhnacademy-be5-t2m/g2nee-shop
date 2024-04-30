package com.t2m.g2nee.shop.review.repository;

import com.t2m.g2nee.shop.review.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long>, ReviewCustomRepository {

}
