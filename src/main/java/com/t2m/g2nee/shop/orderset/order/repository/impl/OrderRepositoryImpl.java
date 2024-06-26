package com.t2m.g2nee.shop.orderset.order.repository.impl;

import static com.t2m.g2nee.shop.orderset.order.domain.Order.OrderState.DELIVERED;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
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
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.EntityManager;
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


    private final JPAQueryFactory queryFactory;

    public OrderRepositoryImpl(EntityManager entityManager) {
        super(Order.class);
        this.queryFactory = new JPAQueryFactory(entityManager);
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
    public Page<GetOrderInfoResponseDto> getOrderListForMembers(Pageable pageable, Long memberId) {
        List<GetOrderInfoResponseDto> queryMemberOrderList = from(order)
                .innerJoin(member).on(order.customer.customerId.eq(member.customerId))
                .leftJoin(couponType).on(order.coupon.couponType.couponTypeId.eq(couponType.couponTypeId))
                .where(order.customer.customerId.eq(memberId))
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
    public GetOrderInfoResponseDto getOrderInfoById(Long orderId) {
        return from(order)
                .innerJoin(customer).on(order.customer.customerId.eq(customer.customerId))
                .leftJoin(couponType).on(order.coupon.couponType.couponTypeId.eq(couponType.couponTypeId))
                .innerJoin(orderDetail).on(order.orderId.eq(orderDetail.order.orderId))
                .where(order.orderId.eq(orderId))
                .select(Projections.fields(GetOrderInfoResponseDto.class,
                        order.orderId,
                        order.orderNumber,
                        customer.customerId.as("customerId"),
                        customer.name.as("customerName"),
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
                        customer.customerId.as("customerId"),
                        customer.name.as("customerName"),
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

    /**
     * {@inheritDoc}
     */
    @Override
    public BigDecimal getTotalOrderAmount(LocalDateTime currentTime, Long memberId) {
        QOrder order = QOrder.order;
        LocalDateTime threeMonthsAgo = currentTime.minusMonths(3);

        return queryFactory.select(order.netAmount.sum().coalesce(BigDecimal.ZERO))
                .from(order)
                .where(order.orderState.eq(DELIVERED)
                        .and(order.customer.customerId.eq(memberId))
                        .and(order.orderDate.between(threeMonthsAgo, currentTime)))
                .fetchOne();
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
