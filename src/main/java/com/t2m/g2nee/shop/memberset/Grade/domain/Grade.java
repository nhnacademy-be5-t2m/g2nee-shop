package com.t2m.g2nee.shop.memberset.Grade.domain;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Grades")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Grade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long gradeId;

    @Enumerated(EnumType.STRING)
    private GradeName gradeName;


    public enum GradeName {
        NORMAL("일반"), ROYAL("로얄"), GOLD("골드"), PLATINUM("플래티넘");

        private final String name;

        GradeName(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

}
