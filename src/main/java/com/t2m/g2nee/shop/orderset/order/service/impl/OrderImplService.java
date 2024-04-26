package com.t2m.g2nee.shop.orderset.order.service.impl;

import com.t2m.g2nee.shop.memberset.Customer.repository.CustomerRepository;
import com.t2m.g2nee.shop.memberset.Member.repository.MemberRepository;
import com.t2m.g2nee.shop.orderset.order.dto.response.GetOrderInfoResponseDto;
import com.t2m.g2nee.shop.orderset.order.dto.response.GetOrderListForAdminResponseDto;
import com.t2m.g2nee.shop.orderset.order.repository.OrderRepository;
import com.t2m.g2nee.shop.orderset.order.service.OrderService;
import com.t2m.g2nee.shop.orderset.orderdetail.repository.OrderDetailRepository;
import com.t2m.g2nee.shop.pageUtils.PageResponse;
import com.t2m.g2nee.shop.policyset.deliveryPolicy.service.DeliveryPolicyService;
import com.t2m.g2nee.shop.policyset.pointPolicy.repository.PointPolicyRepository;
import java.util.UUID;
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
    private final DeliveryPolicyService deliveryPolicyService;
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
    String orderNumber = UUID.randomUUID().toString().replace("-", "");
//        DeliveryPolicy deliveryPolicy = deliveryPolicyService.getDeliveryPolicy(orderCreateRequestDto.)
//
//        Order order = orderRepository.save(new Order());
//
//        return null;
//    }

    @Override
    public PageResponse<GetOrderListForAdminResponseDto> getALlOrderList(int page) {
        GetOrderListForAdminResponseDto orderListForAdminResponseDto = new GetOrderListForAdminResponseDto();
        int size = 5;
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by("createdAt"));
        Page<GetOrderListForAdminResponseDto> returnAdminList =
                orderRepository.getAllOrderList(Pageable.ofSize(page));
        PageResponse<GetOrderListForAdminResponseDto> pageResponse = new PageResponse<>();
        return pageResponse.getPageResponse(page, 4, returnAdminList);
    }
//
//    @Override
//    public PageResponse<GetOrderListForAdminResponseDto> getAllOrdersByState(Pageable pageable,
//                                                                             Order.OrderState orderState) {
//        Page<GetOrderListForAdminResponseDto> returnList =
//                orderRepository.getAllOrderList(pageable);
//        return new PageResponse<>(returnList);
//    }

    @Override
    public PageResponse<GetOrderInfoResponseDto> getOrderListForMembers(int page, Long customerId) {
        GetOrderInfoResponseDto orderListDto = new GetOrderInfoResponseDto();
        int size = 5;
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by("createdAt"));
        Page<GetOrderInfoResponseDto> returnOrderList =
                orderRepository.getOrderListForMembers(Pageable.ofSize(page), customerId);
        PageResponse<GetOrderInfoResponseDto> pageResponse = new PageResponse<>();
        return pageResponse.getPageResponse(page, 4, returnOrderList);
    }


    @Override
    public GetOrderInfoResponseDto getOrderInfoById(Long orderId, Long customerId) {
        GetOrderInfoResponseDto orderInfoResponseDto =
                orderRepository.getOrderInfoById(orderId, customerId);
        return orderInfoResponseDto;
    }

//    //주문 번호로 조회
//    @Override
//    public GetOrderInfoResponseDto getOrderInfoByOrderNumber(String orderNumber) {
//        GetOrderInfoResponseDto orderInfoResponseDto = orderRepository.getOrderInfoByOrderNumber(orderNumber)
//                .orElseThrow(() -> new NotFoundException("존재하지 않습니다"));
//        orderInfoResponseDto.
//        return null;
//    }

//    @Override
//    @Transactional
//    public void changeOrderState(Long orderId, Order.OrderState orderState) {
//        //order 존재 여부 확인?
//        Order order = orderRepository.findById(orderId).orElseThrow(()
//                -> new NotFoundException("주문이 존재하지 않습니다."));
//
//        order.setOrderState(orderState);
//        orderRepository.save(order);
//    }


}

