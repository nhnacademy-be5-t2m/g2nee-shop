package com.t2m.g2nee.shop.OrderSet.Return;

import com.t2m.g2nee.shop.OrderSet.OrderDetail.domain.OrderDetail;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "PackageTypes")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Return {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long returnId;
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
