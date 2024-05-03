package com.t2m.g2nee.shop.orderset.orderdetail.repository.impl;

import com.querydsl.core.types.Projections;
import com.t2m.g2nee.shop.bookset.book.domain.QBook;
import com.t2m.g2nee.shop.memberset.customer.domain.QCustomer;
import com.t2m.g2nee.shop.orderset.order.domain.QOrder;
import com.t2m.g2nee.shop.orderset.orderdetail.domain.OrderDetail;
import com.t2m.g2nee.shop.orderset.orderdetail.domain.QOrderDetail;
import com.t2m.g2nee.shop.orderset.orderdetail.dto.response.GetOrderDetailResponseDto;
import com.t2m.g2nee.shop.orderset.orderdetail.repository.OrderDetailCustomRepository;
import com.t2m.g2nee.shop.orderset.packagetype.domain.QPackageType;
import java.util.List;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

public class OrderDetailRepositoryImpl extends QuerydslRepositorySupport
        implements OrderDetailCustomRepository {
    QOrder order = QOrder.order;
    QOrderDetail orderDetail = QOrderDetail.orderDetail;
    QCustomer customer = QCustomer.customer;
    QBook book = QBook.book;
    QPackageType packageType = QPackageType.packageType;

    public OrderDetailRepositoryImpl() {
        super(OrderDetail.class);
    }


    @Override
    public List<GetOrderDetailResponseDto> getOrderDetailListByOrderId(Long orderId) {
        return from(orderDetail)
                .innerJoin(packageType).on(orderDetail.packageType.packageId.eq(packageType.packageId))
                .innerJoin(book).on(orderDetail.book.bookId.eq(book.bookId))
                .where(orderDetail.order.orderId.eq(orderId))/////
                .select(Projections.fields(GetOrderDetailResponseDto.class,
                        orderDetail.orderDetailId,
                        orderDetail.price,
                        orderDetail.quantity,
                        orderDetail.isCancelled,
                        book.title.as("bookName"),
                        packageType.name.as("packageName"))).fetch();
    }

    @Override
    public List<OrderDetail> findByOrder_OrderId(Long orderId) {
        return null;
    }
}
