package com.t2m.g2nee.shop.orderset.order.service.impl;

import com.t2m.g2nee.shop.exception.NotFoundException;
import com.t2m.g2nee.shop.memberset.Customer.repository.CustomerRepository;
import com.t2m.g2nee.shop.memberset.Member.repository.MemberRepository;
import com.t2m.g2nee.shop.orderset.order.domain.Order;
import com.t2m.g2nee.shop.orderset.order.dto.response.GetOrderInfoResponseDto;
import com.t2m.g2nee.shop.orderset.order.dto.response.GetOrderListForAdminResponseDto;
import com.t2m.g2nee.shop.orderset.order.repository.OrderRepository;
import com.t2m.g2nee.shop.orderset.order.service.OrderService;
import com.t2m.g2nee.shop.orderset.orderdetail.repository.OrderDetailRepository;
import com.t2m.g2nee.shop.pageUtils.PageResponse;
import com.t2m.g2nee.shop.policyset.pointPolicy.repository.PointPolicyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    private final PointPolicyRepository pointPolicyRepository;
    //private final CouponRepository couponRepository;


    //    @Override
//    @Transactional
//    public Long createOrder(OrderCreateRequestDto orderCreateRequestDto) {
//        //
//        Customer customer = null;
//        if (Objects.nonNull(orderCreateRequestDto.getCustomerId())) {
//            customer = customerRepository.findById(orderCreateRequestDto.getCustomerId())
//                    .orElseThrow(() -> new NotFoundException("회원이 아닙니다."));
//        }
//    String orderNumber = UUID.randomUUID().toString().replace("-", "");
//        DeliveryPolicy deliveryPolicy = deliveryPolicyService.getDeliveryPolicy(orderCreateRequestDto.)
//
//        Order order = orderRepository.save(new Order());
//
//        return null;
//    }

    @Override
    public PageResponse<GetOrderListForAdminResponseDto> getALlOrderList(int page) {
        int size = 5;
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by("createdAt"));
        Page<GetOrderListForAdminResponseDto> returnAdminList =
                orderRepository.getAllOrderList(pageable);
        PageResponse<GetOrderListForAdminResponseDto> pageResponse = new PageResponse<>();
        return pageResponse.getPageResponse(page, 4, returnAdminList);
    }

    @Override
    public PageResponse<GetOrderListForAdminResponseDto> getAllOrdersByState(int page,
                                                                             Order.OrderState orderState) {
        int size = 5;
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by("createdAt"));
        Page<GetOrderListForAdminResponseDto> returnAdminList =
                orderRepository.getOrderListByState(pageable, orderState);
        PageResponse<GetOrderListForAdminResponseDto> pageResponse = new PageResponse<>();
        return pageResponse.getPageResponse(page, 4, returnAdminList);
    }

    @Override
    public PageResponse<GetOrderInfoResponseDto> getOrderListForMembers(int page, Long customerId) {
        int size = 5;
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by("createdAt"));
        Page<GetOrderInfoResponseDto> returnOrderList =
                orderRepository.getOrderListForMembers(pageable, customerId);
        PageResponse<GetOrderInfoResponseDto> pageResponse = new PageResponse<>();
        return pageResponse.getPageResponse(page, 4, returnOrderList);
    }


    @Override
    public GetOrderInfoResponseDto getOrderInfoById(Long orderId, Long customerId) {
        return orderRepository.getOrderInfoById(orderId, customerId);
    }

    //주문 번호로 조회
    @Override
    public GetOrderInfoResponseDto getOrderInfoByOrderNumber(String orderNumber) {
        return orderRepository.getOrderInfoByOrderNumber(orderNumber);
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

