package com.t2m.g2nee.shop.point.domain;

import com.t2m.g2nee.shop.memberset.member.domain.Member;
import lombok.*;

import javax.persistence.*;
import com.t2m.g2nee.shop.memberset.member.domain.Member;
import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
