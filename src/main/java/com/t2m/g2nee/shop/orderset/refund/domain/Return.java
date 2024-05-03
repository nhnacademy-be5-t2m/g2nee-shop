package com.t2m.g2nee.shop.orderset.refund.domain;

import com.t2m.g2nee.shop.orderset.orderdetail.domain.OrderDetail;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
@Table(name = "Returns")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Return {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long returnId;
    @Enumerated(EnumType.STRING)
    private ReturnStatus status;
    private Long returnQuantity;
    private String returnReason;

    @ManyToOne
    @JoinColumn(name = "orderDetailId")
    private OrderDetail orderDetail;

    public enum ReturnStatus {
        WAITING, RETURNED
    }
}
