package com.t2m.g2nee.shop.orderset.order.dto.request;

import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 전체 주문 내용 변경하는 dto
 *
 * @author 박재희
 * @since 1.0
 */
@Getter
@NoArgsConstructor
public class OrderChangeRequestDto {
    @NotNull(message = "도서 주문 변경")
    private Long orderDetailId;
    @NotNull(message = "쿠폰")
    private String couponId;

}
