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
    private String authName;
}

