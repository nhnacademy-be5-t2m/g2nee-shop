package com.t2m.g2nee.shop.orderset.order.service.impl;

import com.t2m.g2nee.shop.couponset.coupon.domain.Coupon;
import com.t2m.g2nee.shop.couponset.coupon.service.CouponService;
import com.t2m.g2nee.shop.exception.NotFoundException;
import com.t2m.g2nee.shop.memberset.customer.domain.Customer;
import com.t2m.g2nee.shop.memberset.customer.service.CustomerService;
import com.t2m.g2nee.shop.orderset.order.domain.Order;
import com.t2m.g2nee.shop.orderset.order.dto.request.OrderSaveDto;
import com.t2m.g2nee.shop.orderset.order.dto.response.GetOrderInfoResponseDto;
import com.t2m.g2nee.shop.orderset.order.dto.response.GetOrderListForAdminResponseDto;
import com.t2m.g2nee.shop.orderset.order.dto.response.OrderForPaymentDto;
import com.t2m.g2nee.shop.orderset.order.repository.OrderRepository;
import com.t2m.g2nee.shop.orderset.order.service.OrderService;
import com.t2m.g2nee.shop.orderset.orderdetail.dto.response.GetOrderDetailResponseDto;
import com.t2m.g2nee.shop.orderset.orderdetail.service.OrderDetailService;
import com.t2m.g2nee.shop.pageUtils.PageResponse;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;
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
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final CustomerService customerService;
    private final OrderDetailService orderDetailService;
    private final CouponService couponService;

    private static final int MAXPAGEBUTTONS = 5;


    @Override
    @Transactional(readOnly = true)
    public PageResponse<GetOrderListForAdminResponseDto> getALlOrderList(int page) {
        int size = 5;
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by("createdAt"));
        Page<GetOrderListForAdminResponseDto> returnAdminList =
                orderRepository.getAllOrderList(pageable);
        PageResponse<GetOrderListForAdminResponseDto> pageResponse = new PageResponse<>();
        return pageResponse.getPageResponse(page, 4, returnAdminList);
    }

    @Override
    @Transactional(readOnly = true)
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
    @Transactional(readOnly = true)
    public PageResponse<OrderForPaymentDto> getOrderListForMembers(int page, Long memberId) {

        Page<Order> orders = orderRepository.findByCustomer_CustomerId(memberId,
                PageRequest.of(page - 1, 10,Sort.by("orderId").descending()));

        List<OrderForPaymentDto> orderList = orders
                .stream().map((Order order) -> convertOrderInfoDto(order, null))
                .collect(Collectors.toList());

        int startPage = (int) Math.max(1, orders.getNumber() - Math.floor((double) MAXPAGEBUTTONS / 2));
        int endPage = Math.min(startPage + MAXPAGEBUTTONS - 1, orders.getTotalPages());

        if (endPage - startPage + 1 < MAXPAGEBUTTONS) {
            startPage = Math.max(1, endPage - MAXPAGEBUTTONS + 1);
        }

        return PageResponse.<OrderForPaymentDto>builder()
                .data(orderList)
                .currentPage(page)
                .totalPage(orders.getTotalPages())
                .startPage(startPage)
                .endPage(endPage)
                .totalElements(orders.getTotalElements())
                .build();
    }


    @Override
    @Transactional(readOnly = true)
    public GetOrderInfoResponseDto getOrderInfoById(Long orderId) {
        orderRepository.findById(orderId).orElseThrow(()
                -> new NotFoundException("주문이 존재하지 않습니다."));

        return orderRepository.getOrderInfoById(orderId);

    }

    //주문 번호로 조회
    @Override
    @Transactional(readOnly = true)
    public GetOrderInfoResponseDto getOrderInfoByOrderNumber(String orderNumber) {
        return orderRepository.getOrderInfoByOrderNumber(orderNumber);
    }

    @Override
    @Transactional
    public void changeOrderState(Long orderId, Order.OrderState orderState) {
        Order order = orderRepository.findById(orderId).orElseThrow(()
                -> new NotFoundException("주문이 존재하지 않습니다."));

        order.changeState(orderState);
        orderRepository.save(order);
    }


    @Override
    public void deleteOrder(Long orderId) {
        orderRepository.deleteById(orderId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public OrderForPaymentDto saveOrder(OrderSaveDto orderSaveDto) {
        //회원 유효한지 확인
        Customer customer = customerService.getCustomerInfo(orderSaveDto.getCustomerId());

        //쿠폰 확인
        Coupon coupon = null;
        if (Objects.nonNull(orderSaveDto.getCouponId())) {
            coupon = couponService.getCoupon(orderSaveDto.getCouponId());
        }

        //배송 날짜 확인
        LocalDateTime wishDate = LocalDateTime.now().plusDays(1L);
        if (Objects.nonNull(orderSaveDto.getDeliveryWishDate())) {
            wishDate = LocalDateTime.parse(orderSaveDto.getDeliveryWishDate() + "T00:00:00");
        }

        //주문 저장
        Order order = orderRepository.save(
                Order.builder()
                        .orderNumber(UUID.randomUUID().toString())
                        .orderDate(LocalDateTime.now())
                        .deliveryWishDate(wishDate)
                        .deliveryFee(BigDecimal.valueOf(orderSaveDto.getDeliveryFee()))
                        .orderState(Order.OrderState.PAYWAITING)
                        .netAmount(BigDecimal.valueOf(orderSaveDto.getNetAmount()))
                        .orderAmount(BigDecimal.valueOf(orderSaveDto.getOrderAmount()))
                        .receiverName(orderSaveDto.getReceiverName())
                        .receiverPhoneNumber(orderSaveDto.getReceiverPhoneNumber())
                        .receiveAddress(orderSaveDto.getReceiverAddress())
                        .zipcode(orderSaveDto.getZipcode())
                        .detailAddress(orderSaveDto.getDetailAddress())
                        .message(orderSaveDto.getMessage())
                        .customer(customer)
                        .coupon(coupon)
                        .build()
        );

        //주문 상세 저장
        List<GetOrderDetailResponseDto> orderDetails =
                orderDetailService.saveOrderDetails(order, orderSaveDto.getOrderDetailList());

        return convertOrderInfoDto(order, orderDetails);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public Order getOrder(String orderNumber) {
        return orderRepository.findByOrderNumber(orderNumber)
                .orElseThrow(() -> new NotFoundException("주문이 존재하지 않습니다."));
    }

    @Override
    public void applyUseCoupon(Order order) {
        //쿠폰이 있을 경우에 사용 처리
        Coupon coupon = order.getCoupon();
        if (coupon != null) {
            couponService.useCoupon(coupon.getCouponId());

            //사용후, 만약에 쿠폰을 사용한 다른 주문이 있을 경우 결제 실패 처리
            List<Order> remainOrders =
                    orderRepository.findByCoupon_CouponIdAndOrderIdNot(coupon.getCouponId(), order.getOrderId());
            abortOrders(remainOrders);

        } else {//전체 적용 쿠폰이 null일 경우, orderDetail 쿠폰 확인
            List<Order> remainOrders = orderDetailService.applyUseCoupon(order);
            //사용후, 만약에 쿠폰을 사용한 다른 주문이 있을 경우 결제 실패 처리
            abortOrders(remainOrders);
        }
    }

    @Override
    public String getOrderName(Long orderId) {
        return orderDetailService.getOrderName(orderId);
    }


    private void abortOrders(List<Order> remainOrders) {
        if (!remainOrders.isEmpty()) {
            for (Order remainOrder : remainOrders) {
                changeOrderState(remainOrder.getOrderId(), Order.OrderState.ABORTED);
                //주문 상세는 주문 취소 처리
                orderDetailService.cancelAllOrderDetail(remainOrder.getOrderId());
            }
        }
    }




    /**
     * 주문과 주문 상세 객체를 OrderForPaymentDto로 변환합니다.
     *
     * @param order        주문
     * @param orderDetails 주문상세
     * @return OrderForPaymentDto
     * @author : 김수빈
     * @since : 1.0
     */
    private OrderForPaymentDto convertOrderInfoDto(Order order, List<GetOrderDetailResponseDto> orderDetails) {
        String couponName = null;
        if (Objects.nonNull(order.getCoupon())) {
            couponName = order.getCoupon().getCouponType().getName();
        }

        return new OrderForPaymentDto(
                orderDetailService.getOrderName(order.getOrderId()),
                order.getOrderNumber(), order.getOrderAmount(), order.getCustomer().getCustomerId(),
                orderDetails,
                order.getOrderId(), order.getOrderDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")),
                order.getDeliveryWishDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")),
                order.getDeliveryFee(),
                order.getOrderState().getName(), order.getNetAmount(), order.getReceiverName(),
                order.getReceiverPhoneNumber(),
                order.getReceiveAddress(), order.getZipcode(), order.getDetailAddress(), order.getMessage(), couponName,
                order.getCustomer().getEmail(), order.getCustomer().getPhoneNumber(), order.getCustomer().getName()
        );
    }

}