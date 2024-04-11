package com.t2m.g2nee.shop.orderset.order.service.impl;

import com.t2m.g2nee.shop.exception.NotFoundException;
import com.t2m.g2nee.shop.memberset.Customer.domain.Customer;
import com.t2m.g2nee.shop.memberset.Customer.repository.CustomerRepository;
import com.t2m.g2nee.shop.memberset.Member.repository.MemberRepository;
import com.t2m.g2nee.shop.orderset.order.domain.Order;
import com.t2m.g2nee.shop.orderset.order.dto.request.OrderCreateRequestDto;
import com.t2m.g2nee.shop.orderset.order.dto.response.GetOrderInfoResponseDto;
import com.t2m.g2nee.shop.orderset.order.dto.response.GetOrderListForAdminResponseDto;
import com.t2m.g2nee.shop.orderset.order.dto.response.GetOrderListResponseDto;
import com.t2m.g2nee.shop.orderset.order.repository.OrderRepository;
import com.t2m.g2nee.shop.orderset.order.service.OrderService;
import com.t2m.g2nee.shop.orderset.orderdetail.repository.OrderDetailRepository;
import com.t2m.g2nee.shop.pageUtils.PageResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class OrderImplService implements OrderService {
    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final MemberRepository memberRepository;
    private final OrderDetailRepository orderDetailRepository;

    @Override
    @Transactional
    public Long createOrder(OrderCreateRequestDto orderCreateRequestDto) {
        //
        Customer customer = null;
        //if (Objects.nonNull(orderCreateRequestDto.getCustomerId())) {
        //    customer = customerRepository.getById()
        //}
        return null;
    }

    @Override
    public PageResponse<GetOrderListForAdminResponseDto> getALlOrderList(Pageable pageable) {
        Page<GetOrderListForAdminResponseDto> returnList =
                orderRepository.getAllOrderList(pageable);
        return new PageResponse<>(returnList);
    }

    @Override
    public PageResponse<GetOrderListForAdminResponseDto> getAllOrdersByState(Pageable pageable,
                                                                             Order.OrderState orderState) {
        return null;
    }

    @Override
    public PageResponse<GetOrderListResponseDto> getOrderListForMembers(Pageable pageable, Long customerId) {
        return null;
    }

    @Override
    public GetOrderInfoResponseDto getOrderInfoById(Long orderId, Long customerId) {
        //GetOrderInfoResponseDto orderInfoResponseDto = orderRepository.ge
        return null;
    }

    @Override
    public GetOrderInfoResponseDto getOrderInfoByOrderNumber(String orderNumber) {
        return null;
    }

    @Override
    @Transactional
    public void changeOrderState(Long orderId, Order.OrderState orderState) {
        //order 존재 여부 확인?
        Order order = orderRepository.findById(orderId).orElseThrow(()
                -> new NotFoundException("주문이 존재하지 않습니다."));

        order.setOrderState(orderState);
        orderRepository.save(order);
    }


}
