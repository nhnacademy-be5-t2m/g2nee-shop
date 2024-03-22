package com.t2m.g2nee.shop.MemberSet.Grade.domain;

import com.t2m.g2nee.shop.MemberSet.Member.domain.Member;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "Grade")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Grade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long gradeId;
    private String gradeName;

    @OneToOne
    @JoinColumn(name = "customerId")
    private Member member;
}
