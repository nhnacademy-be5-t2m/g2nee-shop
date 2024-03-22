package com.t2m.g2nee.shop.OrderSet.Order.domain;

import com.t2m.g2nee.shop.CouponSet.Coupon.domain.Coupon;
import com.t2m.g2nee.shop.MemberSet.Customer.domain.Customer;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
@Table(name = "BookOrders")
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
