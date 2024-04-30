package com.t2m.g2nee.shop.memberset.authmember.domain;

import com.t2m.g2nee.shop.memberset.auth.domain.Auth;
import com.t2m.g2nee.shop.memberset.member.domain.Member;
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
@Table(name = "AuthMember")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long authMemberId;

    @ManyToOne
    @JoinColumn(name = "authId")
    private Auth auth;

    @ManyToOne
    @JoinColumn(name = "memberId")
    private Member member;

    @Builder
    public AuthMember(Auth auth, Member member) {
        this.auth = auth;
        this.member = member;
    }
}
