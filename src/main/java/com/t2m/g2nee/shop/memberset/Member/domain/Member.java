package com.t2m.g2nee.shop.memberset.Member.domain;

import com.t2m.g2nee.shop.memberset.Customer.domain.Customer;
import com.t2m.g2nee.shop.memberset.Grade.domain.Grade;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.DynamicInsert;

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

    public enum Gender{
        Male, Female;
    }


    public enum MemberStatus{

        ACTIVE, QUIT, INACTIVE
    }



    @Builder
    public Member(String email, String name, String password, String phoneNumber, String username, String nickname, String birthday, Grade grade, boolean isOAuth, String gender) {
        super(email, name, password, phoneNumber);
        this.username = username;
        this.nickname = nickname;
        this.birthday = birthday;
        this.grade = grade;
        this.isOAuth = isOAuth;
        this.gender = Gender.valueOf(gender);
    }
}
