package com.t2m.g2nee.shop.payment.domain;

import com.t2m.g2nee.shop.memberset.customer.domain.Customer;
import java.time.LocalDateTime;
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
@Table(name = "Payments")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paymentId;
    private int amount;
    private String payType;
    private LocalDateTime paymentDate;
    private String paymentKey;
    private payStatus payStatus;

    @ManyToOne
    @JoinColumn(name = "customerId")
    private Customer customer;

    //TODO: order Mapping

    public enum payStatus {

        COMPLETE, CANCELLED
    }
}
