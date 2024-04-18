package com.t2m.g2nee.shop.orderset.packageType.domain;

import java.math.BigDecimal;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "PackageTypes")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PackageType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long packageId;
    private String name;
    private BigDecimal price;
    private Boolean isActivated;

    public PackageType(String name, BigDecimal price, Boolean isActivated) {
        this.name = name;
        this.price = price;
        this.isActivated = isActivated;
    }
}