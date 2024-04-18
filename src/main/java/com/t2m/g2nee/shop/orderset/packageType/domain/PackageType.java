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

/**
 * PackageTypes의 Entity 입니다.
 *
 * @author : 김수빈
 * @since : 1.0
 */
@Entity
@Table(name = "PackageTypes")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PackageType {

    /**
     * 포장지 식별자
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long packageId;
    /**
     * 포장지 이름
     */
    private String name;
    /**
     * 포장지 가격
     */
    private BigDecimal price;
    /**
     * 포장지 활성화 여부
     */
    private Boolean isActivated;

    public PackageType(String name, BigDecimal price, Boolean isActivated) {
        this.name = name;
        this.price = price;
        this.isActivated = isActivated;
    }
}
