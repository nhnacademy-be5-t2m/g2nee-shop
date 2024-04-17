package com.t2m.g2nee.shop.orderset.order.repository.impl;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import com.t2m.g2nee.shop.orderset.order.domain.Order;
import com.t2m.g2nee.shop.orderset.order.dto.response.GetOrderInfoResponseDto;
import com.t2m.g2nee.shop.orderset.order.dto.response.GetOrderListForAdminResponseDto;
import com.t2m.g2nee.shop.orderset.order.dto.response.GetOrderListResponseDto;
import com.t2m.g2nee.shop.orderset.order.repository.OrderCustomRepository;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

/**
 * repository에서 querydsl 사용하기 위한 클래스
 *
 * @author 박재희
 * @since 1.0
 */
public class OrderImplRepository extends QuerydslRepositorySupport
        implements OrderCustomRepository {
    Order order = new Order();


    public OrderImplRepository() {
        super(Order.class);
    }


    @Override
    public Page<GetOrderListForAdminResponseDto> getAllOrderList(Pageable pageable) {
        JPQLQuery<GetOrderListForAdminResponseDto> queryAdmin = from(order)
                .select(Projections.constructor(GetOrderListForAdminResponseDto.class, order.orderId.as("orderId"),
                        order.orderDate));

        return null;
    }

    @Override
    public Page<GetOrderListForAdminResponseDto> getOrderListByState(Pageable pageable, Order.OrderState orderState) {
        return null;
    }


    @Override

    public Page<GetOrderListResponseDto> getOrderListForMembers(Pageable pageable, Long customerId) {
        return null;
    }

    @Override
    public Optional<GetOrderInfoResponseDto> getOrderInfoById(Long orderId) {
        return Optional.empty();
    }

    @Override
    public Optional<GetOrderInfoResponseDto> getOrderInfoByOrderNumber(String orderNumber) {
        return Optional.empty();
    }
}
