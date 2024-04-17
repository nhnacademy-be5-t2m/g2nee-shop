package com.t2m.g2nee.shop.memberset.Auth.domain;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    public enum AuthName {
        ROLE_ADMIN("관리자"), ROLE_MEMBER("회원");

        private final String name;

        AuthName(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }
}

