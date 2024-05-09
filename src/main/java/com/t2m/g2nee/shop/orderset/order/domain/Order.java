package com.t2m.g2nee.shop.orderset.order.domain;

import com.t2m.g2nee.shop.couponset.coupon.domain.Coupon;
import com.t2m.g2nee.shop.memberset.customer.domain.Customer;
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
    private LocalDateTime orderDate;
    private LocalDateTime deliveryWishDate;
    private BigDecimal deliveryFee; //배송비
    @Enumerated(EnumType.STRING)
    private OrderState orderState;
    private BigDecimal netAmount;   //순수 금액
    private BigDecimal orderAmount; //주문금액
    private String receiverName;
    private String receiverPhoneNumber;
    private String receiveAddress;
    private String zipcode;
    private String detailAddress;
    private String message;

    @ManyToOne
    @JoinColumn(name = "customerId")
    private Customer customer;

    @OneToOne
    @JoinColumn(name = "couponId")
    private Coupon coupon;

    public enum OrderState {
        WAITING("배송대기"), DELIVERING("배송중"), DELIVERED("배송완료"),
        RETURNING("반품대기"), RETURNED("반품완료");
        private final String name;

        OrderState(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }


    public void changeState(OrderState orderState) {
        this.orderState = orderState;
    }

}
