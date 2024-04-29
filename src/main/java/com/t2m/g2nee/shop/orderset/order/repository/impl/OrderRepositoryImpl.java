package com.t2m.g2nee.shop.orderset.order.repository.impl;

import com.querydsl.core.types.Projections;
import com.t2m.g2nee.shop.couponset.Coupon.domain.QCoupon;
import com.t2m.g2nee.shop.couponset.CouponType.domain.QCouponType;
import com.t2m.g2nee.shop.memberset.Customer.domain.QCustomer;
import com.t2m.g2nee.shop.memberset.Member.domain.QMember;
import com.t2m.g2nee.shop.orderset.order.domain.Orders;
import com.t2m.g2nee.shop.orderset.order.domain.QOrders;
import com.t2m.g2nee.shop.orderset.order.dto.response.GetOrderInfoResponseDto;
import com.t2m.g2nee.shop.orderset.order.dto.response.GetOrderListForAdminResponseDto;
import com.t2m.g2nee.shop.orderset.order.repository.OrderCustomRepository;
import com.t2m.g2nee.shop.orderset.orderdetail.domain.QOrderDetail;
import java.util.List;
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

    QOrders orders = QOrders.orders;
    QCustomer customer = QCustomer.customer;
    QMember member = QMember.member;
    QOrderDetail orderDetail = QOrderDetail.orderDetail;
    QCoupon coupon = QCoupon.coupon;
    QCouponType couponType = QCouponType.couponType;

    public OrderRepositoryImpl() {
        super(Orders.class);
    }


    @Override
    public Page<GetOrderListForAdminResponseDto> getAllOrderList(Pageable pageable) {

        List<GetOrderListForAdminResponseDto> queryAdmin = from(orders)
                .innerJoin(customer).on(orders.customer.customerId.eq(customer.customerId))
                .leftJoin(couponType).on(orders.coupon.couponType.couponTypeId.eq(couponType.couponTypeId))
                .select(Projections.fields(GetOrderListForAdminResponseDto.class,
                        orders.orderId,
                        orders.orderNumber,
                        customer.customerId,
                        orders.orderDate,
                        orders.orderState,
                        orders.orderAmount,
                        orders.receiverName,
                        orders.receiverPhoneNumber,
                        orders.receiveAddress,
                        orders.detailAddress,
                        orders.zipcode,
                        orders.message,
                        couponType.name.as("couponName"))).fetch();

        Long count = from(orders)
                .select(orders.orderId.count()).fetchOne();

        return new PageImpl<>(queryAdmin, pageable, count);
    }


    @Override
    public Page<GetOrderListForAdminResponseDto> getOrderListByState(Pageable pageable, Orders.OrderState orderState) {
        List<GetOrderListForAdminResponseDto> queryAdmin = from(orders)
                .innerJoin(customer).on(orders.customer.customerId.eq(customer.customerId))
                .leftJoin(couponType).on(orders.coupon.couponType.couponTypeId.eq(couponType.couponTypeId))
                .where(orders.orderState.eq(orderState))
                .select(Projections.fields(GetOrderListForAdminResponseDto.class,
                        orders.orderId,
                        orders.orderNumber,
                        customer.customerId,
                        orders.orderDate,
                        orders.orderState,
                        orders.orderAmount,
                        orders.receiverName,
                        orders.receiverPhoneNumber,
                        orders.receiveAddress,
                        orders.detailAddress,
                        orders.zipcode,
                        orders.message,
                        couponType.name.as("couponName"))).fetch();

        Long count = from(orders)
                .select(orders.orderId.count()).fetchOne();

        return new PageImpl<>(queryAdmin, pageable, count);
    }


    @Override
    public Page<GetOrderInfoResponseDto> getOrderListForMembers(Pageable pageable, Long customerId) {
        List<GetOrderInfoResponseDto> queryMemberOrderList = from(orders)
                .innerJoin(member).on(orders.customer.customerId.eq(member.customerId))
                .leftJoin(couponType).on(orders.coupon.couponType.couponTypeId.eq(couponType.couponTypeId))
                .where(orders.customer.customerId.eq(customerId))
                .select(Projections.fields(GetOrderInfoResponseDto.class,
                        orders.orderId,
                        orders.orderNumber,
                        orders.orderDate,
                        orders.deliveryWishDate,
                        orders.deliveryFee,
                        orders.orderState,
                        orders.orderAmount,
                        orders.receiverName,
                        orders.receiveAddress,
                        orders.receiverPhoneNumber,
                        orders.zipcode,
                        orders.detailAddress,
                        orders.message,
                        couponType.name.as("couponName"))).fetch();

        Long count = from(orders)
                .select(orders.orderId.count()).fetchOne();

        return new PageImpl<>(queryMemberOrderList, pageable, count);
    }

    @Override
    public GetOrderInfoResponseDto getOrderInfoById(Long orderId, Long customerId) {
        return from(orders)
                .innerJoin(member).on(orders.customer.customerId.eq(member.customerId))
                .leftJoin(couponType).on(orders.coupon.couponType.couponTypeId.eq(couponType.couponTypeId))
                .where(orders.orderId.eq(orderId).and(orders.customer.customerId.eq(customerId)))
                .select(Projections.fields(GetOrderInfoResponseDto.class,
                        orders.orderId,
                        orders.orderNumber,
                        orders.orderDate,
                        orders.deliveryWishDate,
                        orders.deliveryFee,
                        orders.orderState,
                        orders.orderAmount,
                        orders.receiverName,
                        orders.receiveAddress,
                        orders.receiverPhoneNumber,
                        orders.zipcode,
                        orders.detailAddress,
                        orders.message,
                        couponType.name.as("couponName"))).fetchOne();
    }

    @Override
    public GetOrderInfoResponseDto getOrderInfoByOrderNumber(String orderNumber) {
        return from(orders)
                .innerJoin(customer).on(orders.customer.customerId.eq(customer.customerId))
                .leftJoin(couponType).on(orders.coupon.couponType.couponTypeId.eq(couponType.couponTypeId))
                .where(orders.orderNumber.eq(orderNumber))
                .select(Projections.fields(GetOrderInfoResponseDto.class,
                        orders.orderId,
                        orders.orderNumber,
                        orders.orderDate,
                        orders.deliveryWishDate,
                        orders.deliveryFee,
                        orders.orderState,
                        orders.orderAmount,
                        orders.receiverName,
                        orders.receiveAddress,
                        orders.receiverPhoneNumber,
                        orders.zipcode,
                        orders.detailAddress,
                        orders.message,
                        couponType.name.as("couponName"))).fetchOne();
    }
}
