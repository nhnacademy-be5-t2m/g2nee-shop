package com.t2m.g2nee.shop.Point.domain;

import com.t2m.g2nee.shop.MemberSet.Member.domain.Member;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Points")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Point {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pointId;

    private int point;
    private ReasonChange reasonChange;
    private LocalDateTime changeDate;

    //TODO: order Mapping

    @ManyToOne
    @JoinColumn(name = "memberId", referencedColumnName = "customerId")
    private Member member;

    public enum ReasonChange {

        REVIEW, SINGUP, PURCHASE
    }
}
