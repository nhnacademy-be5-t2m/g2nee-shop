package com.t2m.g2nee.shop.MemberSet.Member.domain;

import com.t2m.g2nee.shop.MemberSet.Customer.domain.Customer;
import com.t2m.g2nee.shop.MemberSet.Grade.domain.Grade;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Members")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@DiscriminatorValue("Member")
public class Member extends Customer {

    private String username;
    private String nickname;
    private String birthday;
    private int point;
    private MemberStatus memberStatus;
    private LocalDateTime recentLoginDate;

    @OneToOne
    @JoinColumn(name = "gradeId")
    private Grade grade;

    public enum MemberStatus{

        ACTIVE, QUIT, INACTIVE
    }
}
