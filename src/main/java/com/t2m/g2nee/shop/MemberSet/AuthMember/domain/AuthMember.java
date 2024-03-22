package com.t2m.g2nee.shop.MemberSet.AuthMember.domain;

import com.t2m.g2nee.shop.MemberSet.Auth.domain.Auth;
import com.t2m.g2nee.shop.MemberSet.Member.domain.Member;
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
}
