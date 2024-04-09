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
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;

@Entity
@Table(name = "Members")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@DiscriminatorValue("Member")
@DynamicInsert
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
    private boolean isOAuth;
    private Gender gender;

    public enum Gender {
        Male, Female;
    }


    public enum MemberStatus {

        ACTIVE, QUIT, INACTIVE
    }


    @Builder
    public Member(String email, String name, String password, String phoneNumber, String username, String nickname,
                  String birthday, Grade grade, boolean isOAuth, Gender gender) {
        super(email, name, password, phoneNumber);
        this.username = username;
        this.nickname = nickname;
        this.birthday = birthday;
        this.grade = grade;
        this.isOAuth = isOAuth;
        this.gender = gender;
    }
}
