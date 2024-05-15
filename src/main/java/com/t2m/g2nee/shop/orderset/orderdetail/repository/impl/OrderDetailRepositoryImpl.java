package com.t2m.g2nee.shop.orderset.orderdetail.repository.impl;

import com.querydsl.core.types.Projections;
import com.t2m.g2nee.shop.bookset.book.domain.QBook;
import com.t2m.g2nee.shop.couponset.coupon.domain.QCoupon;
import com.t2m.g2nee.shop.couponset.coupontype.domain.QCouponType;
import com.t2m.g2nee.shop.memberset.customer.domain.QCustomer;
import com.t2m.g2nee.shop.orderset.order.domain.Order;
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
    QCoupon coupon = QCoupon.coupon;
    QCouponType couponType = QCouponType.couponType;

    public OrderDetailRepositoryImpl() {
        super(OrderDetail.class);
    }


    @Override
    public List<GetOrderDetailResponseDto> getOrderDetailListByOrderId(Long orderId) {
        return from(orderDetail)
                .innerJoin(packageType).on(orderDetail.packageType.packageId.eq(packageType.packageId))
                .innerJoin(book).on(orderDetail.book.bookId.eq(book.bookId))
                .leftJoin(couponType).on(orderDetail.coupon.couponType.couponTypeId.eq(couponType.couponTypeId))
                .where(orderDetail.order.orderId.eq(orderId))/////
                .select(Projections.fields(GetOrderDetailResponseDto.class,
                        orderDetail.orderDetailId,
                        orderDetail.price,
                        orderDetail.quantity,
                        orderDetail.isCancelled,
                        book.title.as("bookName"),
                        packageType.name.as("packageName"),
                        couponType.name.as("couponName"))).orderBy(orderDetail.orderDetailId.desc()).fetch();
    }

    @Override
    public List<Order> getRemainOrders(Long orderDetailId, Long couponId) {
        QCoupon coupon = QCoupon.coupon;
        return from(orderDetail)
                .where(coupon.couponId.eq(couponId)
                        .and(orderDetail.orderDetailId.ne(orderDetailId)))
                .select(order)
                .distinct()
                .fetch();
    }
}