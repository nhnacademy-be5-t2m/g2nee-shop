package com.t2m.g2nee.shop.point.domain;

import com.t2m.g2nee.shop.memberset.member.domain.Member;
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
    @JoinColumn(name = "memberId")
    private Member member;

    public enum ReasonChange {

        REVIEW, SINGUP, PURCHASE, RETURN
    }
}
