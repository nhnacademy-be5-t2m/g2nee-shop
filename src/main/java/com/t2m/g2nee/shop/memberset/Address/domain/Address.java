package com.t2m.g2nee.shop.memberset.Address.domain;
import com.t2m.g2nee.shop.memberset.Member.domain.Member;
import lombok.*;

import javax.persistence.*;

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
