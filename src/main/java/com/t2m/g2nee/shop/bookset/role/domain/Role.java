package com.t2m.g2nee.shop.bookset.role.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "Roles")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roleId;
    private String roleName;
    private boolean isActivated;
}
