package com.t2m.g2nee.shop.orderset.orderdetail.dto.response;


import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 도서주문 확인하는 dto
 *
 * @author 박재희
 * @since 1.0
 */

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GetOrderDetailResponseDto {

    private Long orderDetailId;
    private BigDecimal price;
    private Integer quantity;
    private Boolean isCancelled;
    private String bookName;
    private String packageName;
    private String couponName;

}