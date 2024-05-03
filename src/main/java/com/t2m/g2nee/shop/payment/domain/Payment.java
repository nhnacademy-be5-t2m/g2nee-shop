package com.t2m.g2nee.shop.payment.domain;

import com.t2m.g2nee.shop.memberset.customers.domain.Customer;
import com.t2m.g2nee.shop.orderset.order.domain.Order;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Payments")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paymentId;
    private BigDecimal amount;

    private String payType;

    private LocalDateTime paymentDate;

    private String paymentKey;

    @Enumerated(EnumType.STRING)
    private PayStatus payStatus;

    @ManyToOne
    @JoinColumn(name = "customerId")
    private Customer customer;

    @OneToOne
    @JoinColumn(name = "orderId", unique = true)
    private Order order;

    public Payment(BigDecimal amount, String payType, LocalDateTime paymentDate,
                   String paymentKey, PayStatus payStatus, Customer customer, Order order) {
        this.amount = amount;
        this.payType = payType;
        this.payStatus = payStatus;
        this.customer = customer;
        this.order = order;
        this.paymentDate = paymentDate;
        this.paymentKey = paymentKey;
    }

    public void setCancel(LocalDateTime paymentDate) {
        this.payStatus = PayStatus.CANCELLED;
        this.paymentDate = paymentDate;
    }

    public enum PayStatus {

        COMPLETE("결제완료"), CANCELLED("결제취소"), WAITING("결제대기"), ABORTED("결제실패");
        private final String name;

        PayStatus(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }
}
