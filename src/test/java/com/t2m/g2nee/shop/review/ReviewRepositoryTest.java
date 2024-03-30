package com.t2m.g2nee.shop.review;

import com.t2m.g2nee.shop.review.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

/**
 * 리뷰 repository 테스트
 *
 * @author 박재희
 * @since: 1.0
 */
@DataJpaTest
public class ReviewRepositoryTest {
    @Autowired
    ReviewRepository reviewRepository;

    @Autowired
    TestEntityManager testEntityManager;

    
}
