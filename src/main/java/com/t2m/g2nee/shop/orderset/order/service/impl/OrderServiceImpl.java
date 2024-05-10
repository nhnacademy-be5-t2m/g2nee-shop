package com.t2m.g2nee.shop.orderset.order.service.impl;

import com.t2m.g2nee.shop.couponset.coupon.domain.Coupon;
import com.t2m.g2nee.shop.couponset.coupon.service.CouponService;
import com.t2m.g2nee.shop.exception.BadRequestException;
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
import com.t2m.g2nee.shop.policyset.deliverypolicy.dto.response.DeliveryPolicyInfoDto;
import com.t2m.g2nee.shop.policyset.deliverypolicy.service.DeliveryPolicyService;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
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
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final CustomerService customerService;
    private final OrderDetailService orderDetailService;
    private final CouponService couponService;
    private final DeliveryPolicyService deliveryPolicyService;

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
    public PageResponse<GetOrderInfoResponseDto> getOrderListForMembers(int page, Long customerId) {
        int size = 5;
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by("createdAt"));
        Page<GetOrderInfoResponseDto> returnOrderList =
                orderRepository.getOrderListForMembers(pageable, customerId);
        PageResponse<GetOrderInfoResponseDto> pageResponse = new PageResponse<>();
        return pageResponse.getPageResponse(page, 4, returnOrderList);
    }


    @Override
    @Transactional(readOnly = true)
    public GetOrderInfoResponseDto getOrderInfoById(Long orderId, Long customerId) {
        return orderRepository.getOrderInfoById(orderId, customerId);
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
            wishDate = LocalDateTime.parse(orderSaveDto.getDeliveryWishDate());
        }

        //배송비 확인
        DeliveryPolicyInfoDto deliveryPolicy = deliveryPolicyService.getDeliveryPolicy();
        int deliveryFee = orderSaveDto.getDeliveryFee();
        boolean overDelivery = (orderSaveDto.getOrderAmount() < deliveryPolicy.getFreeDeliveryStandard()) &&
                (deliveryFee != deliveryPolicy.getDeliveryFee());
        boolean underDelivery = (orderSaveDto.getOrderAmount() >= deliveryPolicy.getFreeDeliveryStandard()) &&
                (deliveryFee != 0);
        if (overDelivery || underDelivery) {
            throw new BadRequestException("배송비가 일치하지 않습니다.");
        }

        //주문 저장
        Order order = orderRepository.save(
                Order.builder()
                        .orderNumber("g2nee-order-" + UUID.randomUUID().toString())
                        .orderDate(LocalDateTime.now())
                        .deliveryWishDate(wishDate)
                        .deliveryFee(BigDecimal.valueOf(deliveryFee))
                        .orderState(Order.OrderState.PAYWAITING)
                        .netAmount(BigDecimal.valueOf(orderSaveDto.getNetAmount()))
                        .orderAmount(BigDecimal.valueOf(orderSaveDto.getOrderAmount()))
                        .receiverName(orderSaveDto.getReceiverName())
                        .receiverPhoneNumber(orderSaveDto.getReceiverPhoneNumber())
                        .receiveAddress(orderSaveDto.getReceiveAddress())
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
            abortOrders(order.getOrderId(), coupon.getCouponId());

        } else {//전체 적용 쿠폰이 null일 경우, orderDetail 쿠폰 확인
            orderDetailService.applyUseCoupon(order);
        }
    }

    @Override
    public void abortOrders(Long orderId, Long couponId) {
        List<Order> remainOrders = orderRepository.findByCoupon_CouponIdAndOrderIdNot(couponId, orderId);
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
                order.getReceiveAddress(), order.getZipcode(), order.getDetailAddress(), order.getMessage(), couponName
        );
    }
}