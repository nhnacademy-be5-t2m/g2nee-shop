package com.t2m.g2nee.shop.memberset.Address.domain;

import com.t2m.g2nee.shop.memberset.Member.domain.Member;
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
@Setter
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
    private boolean isDefault;

    @ManyToOne
    @JoinColumn(name = "memberId")
    private Member member;
}
