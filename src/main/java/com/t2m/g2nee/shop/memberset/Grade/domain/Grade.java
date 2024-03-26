package com.t2m.g2nee.shop.memberset.Grade.domain;

import com.t2m.g2nee.shop.memberset.Member.domain.Member;
import lombok.*;

import javax.persistence.*;

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
    private String gradeName;
    
    @OneToOne(mappedBy = "grade", optional = false)
    private Member member;

    public enum GradeName{
        NOMAL, ROYAL, GOLD, PLATINUM

    }    

}
