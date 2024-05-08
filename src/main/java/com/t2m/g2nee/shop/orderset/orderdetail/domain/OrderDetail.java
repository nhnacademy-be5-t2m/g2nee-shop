package com.t2m.g2nee.shop.orderset.orderdetail.domain;

import com.t2m.g2nee.shop.bookset.book.domain.Book;
import com.t2m.g2nee.shop.couponset.coupon.domain.Coupon;
import com.t2m.g2nee.shop.orderset.order.domain.Order;
import com.t2m.g2nee.shop.orderset.packagetype.domain.PackageType;
import java.math.BigDecimal;
import javax.persistence.Entity;
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

@Entity
@Table(name = "OrderDetails")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderDetailId;
    private BigDecimal price;
    private Integer quantity;
    private Boolean isCancelled;

    @ManyToOne
    @JoinColumn(name = "orderId")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "bookId")
    private Book book;

    @ManyToOne
    @JoinColumn(name = "packageTypeId")
    private PackageType packageType;

    @OneToOne
    @JoinColumn(name = "couponId")
    private Coupon coupon;

    public void setIsCancelled() {
        this.isCancelled = true;
    }
}
