package com.t2m.g2nee.shop.memberset.address.domain;

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
@Table(name = "Address")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long addressId;

    private String alias;
    private String zipcode;
    private String address;
    private String detail;
    @Setter
    private Boolean isDefault;

    @ManyToOne
    @JoinColumn(name = "memberId")
    private Member member;
}
