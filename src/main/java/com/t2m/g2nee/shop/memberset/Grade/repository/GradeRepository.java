package com.t2m.g2nee.shop.memberset.Grade.repository;

import com.t2m.g2nee.shop.memberset.Grade.domain.Grade;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GradeRepository extends JpaRepository<Grade,Long> {
    Grade findByGradeId(Long gradeId);
}
