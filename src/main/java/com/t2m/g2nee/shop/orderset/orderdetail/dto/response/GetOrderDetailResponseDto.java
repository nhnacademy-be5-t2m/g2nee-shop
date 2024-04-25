package com.t2m.g2nee.shop.orderset.orderdetail.dto.response;


import com.querydsl.core.annotations.QueryProjection;
import com.t2m.g2nee.shop.bookset.book.domain.Book;
import com.t2m.g2nee.shop.memberset.Customer.domain.Customer;
import com.t2m.g2nee.shop.orderset.packageType.domain.PackageType;
import java.math.BigDecimal;
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
public class GetOrderDetailResponseDto {
    private Long orderDetailId;
    private BigDecimal price;
    private Integer quantity;
    private Boolean isCancelled;
    private Book bookId;
    private PackageType packageId;
    private Customer customerId;

    @QueryProjection
    public GetOrderDetailResponseDto(Long orderDetailId, BigDecimal price,
                                     Integer quantity, Boolean isCancelled,
                                     Book bookId, PackageType packageId,
                                     Customer customerId) {
        this.orderDetailId = orderDetailId;
        this.price = price;
        this.quantity = quantity;
        this.isCancelled = isCancelled;
        this.bookId = bookId;
        this.packageId = packageId;
        this.customerId = customerId;
    }


}
