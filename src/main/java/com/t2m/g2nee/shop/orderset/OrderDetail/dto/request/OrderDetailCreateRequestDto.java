package com.t2m.g2nee.shop.orderset.OrderDetail.dto.request;

import java.math.BigDecimal;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 도서주문 생성하는 dto
 *
 * @author 박재희
 * @since 1.0
 */
@Getter
@NoArgsConstructor
public class OrderDetailCreateRequestDto {
    @NotNull(message = "도서 번호는 필수 입력사항입니다.")
    private Long bookId;
    @NotNull(message = "도서 수량는 필수입니다. ")
    private Integer quantity;
    @NotNull(message = "도서 가격정보는 필수입니다.")
    private BigDecimal price;
    @NotNull(message = "도서 포장정보는 필수입니다.")
    private Long packageTypeId;

}
