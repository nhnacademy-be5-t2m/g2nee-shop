package com.t2m.g2nee.shop.orderset.orderdetail.dto.response;

import com.t2m.g2nee.shop.bookset.book.domain.Book;
import com.t2m.g2nee.shop.fileset.file.domain.File;
import com.t2m.g2nee.shop.memberset.Customer.domain.Customer;
import com.t2m.g2nee.shop.orderset.packageType.domain.PackageType;
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
@AllArgsConstructor
@NoArgsConstructor
public class GetOrderDetailResponseDto {
    private Long orderDetailId;
    private BigDecimal price;
    private Integer quantity;
    private Boolean isCancelled;
    private Book bookId;
    private PackageType packageType;
    private Customer customer;
    private File fileId;
}
