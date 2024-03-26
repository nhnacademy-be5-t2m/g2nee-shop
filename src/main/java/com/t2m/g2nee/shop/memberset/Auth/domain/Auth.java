package com.t2m.g2nee.shop.memberset.Auth.domain;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "Auths")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Auth {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long authId;

    @Enumerated(EnumType.STRING)
    private AuthName authName;

    public enum AuthName{
        ROLE_ADMIN, ROLE_MEMBER
    }
}

