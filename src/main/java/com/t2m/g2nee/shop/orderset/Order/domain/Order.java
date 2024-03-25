package com.t2m.g2nee.shop.orderset.Order.domain;

import com.t2m.g2nee.shop.couponset.Coupon.domain.Coupon;
import com.t2m.g2nee.shop.memberset.Customer.domain.Customer;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
@Table(name = "Orders")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;
    private String orderNumber;
    private Timestamp orderDate;
    private Timestamp deliveryWishDate;
    private BigDecimal deliveryFee;
    @Enumerated(EnumType.STRING)
    private OrderState orderState;
    private BigDecimal netAmount;
    private BigDecimal orderAmount;
    private String receiverName;
    private String receiverPhoneNumber;
    private String receiveAddress;
    private String zipcode;
    private String detail;
    private String message;

    @ManyToOne
    @JoinColumn(name = "customerId")
    private Customer customer;

    @OneToOne
    @JoinColumn(name = "couponId")
    private Coupon coupon;

    public enum OrderState{
        WAITING, DELIVERING, DELIVERED, RETURNING, RETURNED
    }



}
