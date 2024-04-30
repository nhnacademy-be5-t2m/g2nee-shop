package com.t2m.g2nee.shop.memberset.authmember.domain;

import com.t2m.g2nee.shop.memberset.auth.domain.Auth;
import com.t2m.g2nee.shop.memberset.member.domain.Member;
import lombok.*;

import javax.persistence.*;

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
