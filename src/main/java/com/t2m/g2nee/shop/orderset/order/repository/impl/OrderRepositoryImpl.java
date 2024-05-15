package com.t2m.g2nee.shop.orderset.order.repository.impl;

import com.querydsl.core.types.Projections;
import com.t2m.g2nee.shop.bookset.book.domain.QBook;
import com.t2m.g2nee.shop.couponset.coupon.domain.QCoupon;
import com.t2m.g2nee.shop.couponset.coupontype.domain.QCouponType;
import com.t2m.g2nee.shop.memberset.customer.domain.QCustomer;
import com.t2m.g2nee.shop.memberset.member.domain.QMember;
import com.t2m.g2nee.shop.orderset.order.domain.Order;
import com.t2m.g2nee.shop.orderset.order.domain.QOrder;
import com.t2m.g2nee.shop.orderset.order.dto.response.GetOrderInfoResponseDto;
import com.t2m.g2nee.shop.orderset.order.dto.response.GetOrderListForAdminResponseDto;
import com.t2m.g2nee.shop.orderset.order.repository.OrderCustomRepository;
import com.t2m.g2nee.shop.orderset.orderdetail.domain.QOrderDetail;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

/**
 * OrderRepository 에서 querydsl 사용하기 위한 클래스
 *
 * @author 박재희
 * @since 1.0
 */
public class OrderRepositoryImpl extends QuerydslRepositorySupport
        implements OrderCustomRepository {

    QOrder order = QOrder.order;
    QCustomer customer = QCustomer.customer;
    QMember member = QMember.member;
    QOrderDetail orderDetail = QOrderDetail.orderDetail;
    QCoupon coupon = QCoupon.coupon;
    QCouponType couponType = QCouponType.couponType;
    QBook book = QBook.book;

    public OrderRepositoryImpl() {
        super(Order.class);
    }


    @Override
    public Page<GetOrderListForAdminResponseDto> getAllOrderList(Pageable pageable) {

        List<GetOrderListForAdminResponseDto> queryAdmin = from(order)
                .innerJoin(customer).on(order.customer.customerId.eq(customer.customerId))
                .leftJoin(couponType).on(order.coupon.couponType.couponTypeId.eq(couponType.couponTypeId))
                .select(Projections.fields(GetOrderListForAdminResponseDto.class,
                        order.orderId,
                        order.orderNumber,
                        customer.customerId,
                        order.orderDate,
                        order.orderState,
                        order.orderAmount,
                        order.receiverName,
                        order.receiverPhoneNumber,
                        order.receiveAddress,
                        order.detailAddress,
                        order.zipcode,
                        order.message,
                        couponType.name.as("couponName"))).fetch();

        Long count = from(order)
                .select(order.orderId.count()).fetchOne();

        return new PageImpl<>(queryAdmin, pageable, count);
    }


    @Override
    public Page<GetOrderListForAdminResponseDto> getOrderListByState(Pageable pageable, Order.OrderState orderState) {
        List<GetOrderListForAdminResponseDto> queryAdmin = from(order)
                .innerJoin(customer).on(order.customer.customerId.eq(customer.customerId))
                .leftJoin(couponType).on(order.coupon.couponType.couponTypeId.eq(couponType.couponTypeId))
                .where(order.orderState.eq(orderState))
                .select(Projections.fields(GetOrderListForAdminResponseDto.class,
                        order.orderId,
                        order.orderNumber,
                        customer.customerId,
                        order.orderDate,
                        order.orderState,
                        order.orderAmount,
                        order.receiverName,
                        order.receiverPhoneNumber,
                        order.receiveAddress,
                        order.detailAddress,
                        order.zipcode,
                        order.message,
                        couponType.name.as("couponName"))).fetch();

        Long count = from(order)
                .select(order.orderId.count()).fetchOne();

        return new PageImpl<>(queryAdmin, pageable, count);
    }


    @Override
    public Page<GetOrderInfoResponseDto> getOrderListForMembers(Pageable pageable, Long customerId) {
        List<GetOrderInfoResponseDto> queryMemberOrderList = from(order)
                .innerJoin(member).on(order.customer.customerId.eq(member.customerId))
                .leftJoin(couponType).on(order.coupon.couponType.couponTypeId.eq(couponType.couponTypeId))
                .where(order.customer.customerId.eq(customerId))
                .select(Projections.fields(GetOrderInfoResponseDto.class,
                        order.orderId,
                        order.orderNumber,
                        order.orderDate,
                        order.deliveryWishDate,
                        order.deliveryFee,
                        order.orderState,
                        order.orderAmount,
                        order.receiverName,
                        order.receiveAddress,
                        order.receiverPhoneNumber,
                        order.zipcode,
                        order.detailAddress,
                        order.message,
                        couponType.name.as("couponName"))).fetch();

        Long count = from(order)
                .select(order.orderId.count()).fetchOne();

        return new PageImpl<>(queryMemberOrderList, pageable, count);
    }

    @Override
    public GetOrderInfoResponseDto getOrderInfoById(Long orderId, Long customerId) {
        return from(order)
                .innerJoin(member).on(order.customer.customerId.eq(member.customerId))
                .leftJoin(couponType).on(order.coupon.couponType.couponTypeId.eq(couponType.couponTypeId))
                .where(order.orderId.eq(orderId).and(order.customer.customerId.eq(customerId)))
                .select(Projections.fields(GetOrderInfoResponseDto.class,
                        order.orderId,
                        order.orderNumber,
                        order.orderDate,
                        order.deliveryWishDate,
                        order.deliveryFee,
                        order.orderState,
                        order.orderAmount,
                        order.receiverName,
                        order.receiveAddress,
                        order.receiverPhoneNumber,
                        order.zipcode,
                        order.detailAddress,
                        order.message,
                        couponType.name.as("couponName"))).fetchOne();
    }

    @Override
    public GetOrderInfoResponseDto getOrderInfoByOrderNumber(String orderNumber) {
        return from(order)
                .innerJoin(customer).on(order.customer.customerId.eq(customer.customerId))
                .leftJoin(couponType).on(order.coupon.couponType.couponTypeId.eq(couponType.couponTypeId))
                .where(order.orderNumber.eq(orderNumber))
                .select(Projections.fields(GetOrderInfoResponseDto.class,
                        order.orderId,
                        order.orderNumber,
                        order.orderDate,
                        order.deliveryWishDate,
                        order.deliveryFee,
                        order.orderState,
                        order.orderAmount,
                        order.receiverName,
                        order.receiveAddress,
                        order.receiverPhoneNumber,
                        order.zipcode,
                        order.detailAddress,
                        order.message,
                        couponType.name.as("couponName"))).fetchOne();
    }

    @Override
    public Optional<Order> findByOrderNumber(String orderNumber) {
        return Optional.empty();
    }

    @Override
    public Integer getMemberBookOrderNum(Long memberId, Long bookId) {

        return from(book)
                .innerJoin(orderDetail).on(orderDetail.book.bookId.eq(book.bookId))
                .innerJoin(order).on(orderDetail.order.orderId.eq(order.orderId))
                .where(order.customer.customerId.eq(memberId)
                        .and(book.bookId.eq(bookId)))
                .select(orderDetail.quantity.sum())
                .groupBy(book)
                .fetchOne();
    }
}
