package com.t2m.g2nee.shop.orderset.order.dto.request;

import com.t2m.g2nee.shop.couponset.Coupon.domain.Coupon;
import com.t2m.g2nee.shop.memberset.Customer.domain.Customer;
import java.util.List;
import java.util.Map;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 전체 주문 생성하는 dto
 *
 * @author 박재희
 * @since 1.0
 */
@Getter
@NoArgsConstructor
public class OrderCreateRequestDto {
    private Customer customerId;
    @NotNull(message = "도서 주문 ")
    private List<Long> orderDetailId;
    @NotNull(message = "도서 번호")
    private Map<Long, Long> bookId;
    @NotNull(message = "도서 수량")
    private Map<Long, Integer> bookAmount;
    @NotNull(message = "쿠폰")
    private Coupon coupon;
    private String orderName;

}
