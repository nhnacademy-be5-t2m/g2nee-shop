package com.t2m.g2nee.shop.orderset.PackageType.domain;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "PackageTypes")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PackageType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long packageId;
    private String name;
    private BigDecimal price;
}
