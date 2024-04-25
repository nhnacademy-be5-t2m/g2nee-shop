package com.t2m.g2nee.shop.orderset.order.repository.impl;

import com.querydsl.core.types.Projections;
import com.t2m.g2nee.shop.memberset.Customer.domain.QCustomer;
import com.t2m.g2nee.shop.orderset.order.domain.Orders;
import com.t2m.g2nee.shop.orderset.order.domain.QOrders;
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
 * repository에서 querydsl 사용하기 위한 클래스
 *
 * @author 박재희
 * @since 1.0
 */
public class OrderRepositoryImpl extends QuerydslRepositorySupport
        implements OrderCustomRepository {

    QOrders orders = QOrders.orders;
    QCustomer customer = QCustomer.customer;
    QOrderDetail orderDetail = QOrderDetail.orderDetail;

    public OrderRepositoryImpl() {
        super(Orders.class);
    }


    @Override
    public Page<GetOrderListForAdminResponseDto> getAllOrderList(Pageable pageable) {

        List<GetOrderListForAdminResponseDto> queryAdmin = from(orders)
                .innerJoin(customer).on(orders.customer.customerId.eq(customer.customerId))
                .select(Projections.fields(GetOrderListForAdminResponseDto.class,
                        orders.orderId,
                        customer.customerId,
                        orders.orderDate,
                        orders.orderState,
                        orders.orderAmount,
                        orders.receiverName,
                        orders.receiverPhoneNumber,
                        orders.receiveAddress,
                        orders.detailAddress,
                        orders.zipcode,
                        orders.message)).fetch();

        Long count = from(orders)
                .select(orders.orderId.count()).fetchOne();

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
    @Override
    public Optional<GetOrderInfoResponseDto> getOrderInfoById(Long orderId) {
        return Optional.empty();
    }

    @Override
    public Optional<GetOrderInfoResponseDto> getOrderInfoByOrderNumber(String orderNumber) {
        return Optional.empty();
    }
}
