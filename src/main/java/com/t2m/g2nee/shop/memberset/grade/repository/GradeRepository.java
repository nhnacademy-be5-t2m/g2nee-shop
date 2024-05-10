package com.t2m.g2nee.shop.memberset.grade.repository;

import com.t2m.g2nee.shop.memberset.grade.domain.Grade;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GradeRepository extends JpaRepository<Grade, Long> {
    Grade findByGradeName(Grade.GradeName gradeName);
}
