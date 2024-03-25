package com.t2m.g2nee.shop.orderset.Return.domain;

import com.t2m.g2nee.shop.orderset.OrderDetail.domain.OrderDetail;
import lombok.*;

import javax.persistence.*;

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

    public enum ReturnStatus{
        WAITING, RETURNED
    }
}
