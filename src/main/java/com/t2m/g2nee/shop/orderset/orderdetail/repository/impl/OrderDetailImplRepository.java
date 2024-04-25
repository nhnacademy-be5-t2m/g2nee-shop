package com.t2m.g2nee.shop.orderset.orderdetail.repository.impl;

import com.t2m.g2nee.shop.orderset.orderdetail.domain.OrderDetail;
import com.t2m.g2nee.shop.orderset.orderdetail.repository.OrderDetailCustomRepository;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

public class OrderDetailImplRepository extends QuerydslRepositorySupport
        implements OrderDetailCustomRepository {
    //    QOrderDetail orderDetail = QOrderDetail.orderDetail;
//    QCustomer customer = QCustomer.customer;
//    QBook book = QBook.book;
//    QPackageType packageType = QPackageType.packageType;
//
    public OrderDetailImplRepository() {
        super(OrderDetail.class);
    }
//
//    @Override
//    public Optional<GetOrderDetailResponseDto> getOrderDetailById(Long orderDetailId) {
//        QOrderDetail orderDetail = QOrderDetail.orderDetail;
//        QCustomer customer = QCustomer.customer;
//        QBook book = QBook.book;
//        QPackageType packageType = QPackageType.packageType;
//        GetOrderDetailResponseDto orderDetailInfo = (GetOrderDetailResponseDto) from(orderDetail)
//                .innerJoin(customer).on(orderDetail.customer.customerId.eq(customer.customerId))
//                .innerJoin(packageType).on(orderDetail.packageType.packageId.eq(packageType.packageId))
//                .select(Projections.fields(GetOrderDetailResponseDto.class,
//                        orderDetail.orderDetailId,
//                        orderDetail.price,
//                        orderDetail.quantity,
//                        orderDetail.isCancelled,
//                        book.bookId,
//                        packageType.packageId,
//                        customer.customerId)).fetch();
//
//        return Optional.empty();
//    }

//    private Optional<GetOrderDetailResponseDto> orderDetailInfo(Long orderDetailId) {
//
//    }
}
