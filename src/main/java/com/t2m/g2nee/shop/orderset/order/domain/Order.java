package com.t2m.g2nee.shop.orderset.order.domain;

import com.t2m.g2nee.shop.couponset.Coupon.domain.Coupon;
import com.t2m.g2nee.shop.memberset.Customer.domain.Customer;
import java.math.BigDecimal;
import java.sql.Timestamp;
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
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    private String orderNumber; //주문번호(비회원 조회용)
    private Timestamp orderDate;
    private Timestamp deliveryWishDate;
    private BigDecimal deliveryFee; //배송비
    @Enumerated(EnumType.STRING)
    private OrderState orderState;
    private BigDecimal netAmount;   //순수 금액
    private BigDecimal orderAmount; //주문금액
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

    public enum OrderState {
        WAITING, DELIVERING, DELIVERED, RETURNING, RETURNED
    }

    @Builder
    public Order(Long orderId, String orderNumber,
                 Timestamp orderDate, Timestamp deliveryWishDate,
                 BigDecimal deliveryFee, OrderState orderState,
                 BigDecimal netAmount, BigDecimal orderAmount,
                 String receiverName, String receiverPhoneNumber,
                 String receiveAddress, String zipcode,
                 String detail, String message,
                 Customer customer, Coupon coupon) {
        this.orderId = orderId;
        this.orderNumber = orderNumber;
        this.orderDate = orderDate;
        this.deliveryWishDate = deliveryWishDate;
        this.deliveryFee = deliveryFee;
        this.orderState = orderState;
        this.netAmount = netAmount;
        this.orderAmount = orderAmount;
        this.receiverName = receiverName;
        this.receiverPhoneNumber = receiverPhoneNumber;
        this.receiveAddress = receiveAddress;
        this.zipcode = zipcode;
        this.detail = detail;
        this.message = message;
        this.customer = customer;
        this.coupon = coupon;
    }

    public void changeState(OrderState orderState) {
        this.orderState = orderState;
    }

}
