package com.t2m.g2nee.shop.MemberSet.Member.domain;

import com.t2m.g2nee.shop.MemberSet.Customer.domain.Customer;
import lombok.*;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "Members")
@Getter
@Setter
@DiscriminatorValue("Member")
public class Member extends Customer {

    private String username;
    private String nickname;
    private String birthday;
    private int point;
    private MemberStatus memberStatus;
    private LocalDateTime recentLoginDate;


    public enum MemberStatus{

        ACTIVE, QUIT, INACTIVE
    }
}
