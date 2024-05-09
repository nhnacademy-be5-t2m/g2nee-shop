package com.t2m.g2nee.shop.memberset.customer.domain;

import com.t2m.g2nee.shop.memberset.member.domain.Member;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Customers")
@Getter
@Setter
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long customerId;
    private String name;

    private String phoneNumber;

    private String email;

    private String password;


    public Customer(String email, String name, String password, String phoneNumber) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.password = password;
    }

    public boolean isCustomerIdNotInMember(Long customerId) {
        return !(this instanceof Member);
    }
}


