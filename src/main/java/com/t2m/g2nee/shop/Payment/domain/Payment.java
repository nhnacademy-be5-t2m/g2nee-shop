package com.t2m.g2nee.shop.Payment.domain;

import com.t2m.g2nee.shop.MemberSet.Member.domain.Member;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

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
    private Member member;

    //TODO: order Mapping

    public enum payStatus{

        COMPLETE, CANCELLED
    }
}
