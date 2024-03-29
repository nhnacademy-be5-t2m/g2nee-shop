package com.t2m.g2nee.shop.memberset.Customer.domain;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

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


    public Customer(String email, String name,String password,String phoneNumber){
        this.name = name;
        this.phoneNumber=phoneNumber;
        this.email=email;
        this.password=password;
    }
}
