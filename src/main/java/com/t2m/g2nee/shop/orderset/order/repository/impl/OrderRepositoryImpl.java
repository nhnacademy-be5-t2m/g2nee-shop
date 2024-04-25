package com.t2m.g2nee.shop.orderset.order.repository.impl;

import com.querydsl.core.types.Projections;
import com.t2m.g2nee.shop.memberset.Customer.domain.QCustomer;
import com.t2m.g2nee.shop.orderset.order.domain.Order;
import com.t2m.g2nee.shop.orderset.order.domain.QOrder;
import com.t2m.g2nee.shop.orderset.order.dto.response.GetOrderListForAdminResponseDto;
import com.t2m.g2nee.shop.orderset.order.repository.OrderCustomRepository;
import com.t2m.g2nee.shop.orderset.orderdetail.domain.QOrderDetail;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

/**
 * repository에서 querydsl 사용하기 위한 클래스
 *
 * @author 박재희
 * @since 1.0
 */
public class OrderRepositoryImpl extends QuerydslRepositorySupport
        implements OrderCustomRepository {

    QOrder order = QOrder.order;
    QCustomer customer = QCustomer.customer;
    QOrderDetail orderDetail = QOrderDetail.orderDetail;

    public OrderRepositoryImpl() {
        super(Order.class);
    }


    @Override
    public Page<GetOrderListForAdminResponseDto> getAllOrderList(Pageable pageable, int page) {

        List<GetOrderListForAdminResponseDto> queryAdmin = from(order)
                .innerJoin(customer).on(order.customer.customerId.eq(customer.customerId))
                .innerJoin(orderDetail).on(order.orderId.eq(orderDetail.order.orderId))
                .select(Projections.fields(GetOrderListForAdminResponseDto.class,
                        order.orderId,
                        customer.customerId,
                        orderDetail.orderDetailId,
                        order.orderDate,
                        order.orderState,
                        order.orderAmount)).fetch();

        Long count = from(order)
                .select(order.orderId.count()).fetchOne();

        return new PageImpl<>(queryAdmin, pageable, count);
    }
//
//    @Override
//    public Page<GetOrderListForAdminResponseDto> getOrderListByState(Pageable pageable, Order.OrderState orderState) {
//        return null;
//    }
//
//
//    @Override
//
//    public Page<GetOrderListResponseDto> getOrderListForMembers(Pageable pageable, Long customerId) {
//        return null;
//    }
//
//    @Override
//    public Optional<GetOrderInfoResponseDto> getOrderInfoById(Long orderId) {
//        return Optional.empty();
//    }
//
//    @Override
//    public Optional<GetOrderInfoResponseDto> getOrderInfoByOrderNumber(String orderNumber) {
//        return Optional.empty();
//    }
}
