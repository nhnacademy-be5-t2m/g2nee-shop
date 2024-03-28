package com.t2m.g2nee.shop.memberset.Member.domain;

import com.t2m.g2nee.shop.memberset.Customer.domain.Customer;
import com.t2m.g2nee.shop.memberset.Grade.domain.Grade;
import java.time.LocalDateTime;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

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
    @Enumerated(EnumType.STRING)
    private MemberStatus memberStatus;
    private LocalDateTime recentLoginDate;

    @OneToOne
    @JoinColumn(name = "gradeId")
    private Grade grade;

    public enum MemberStatus {

        ACTIVE, QUIT, INACTIVE
    }
}
