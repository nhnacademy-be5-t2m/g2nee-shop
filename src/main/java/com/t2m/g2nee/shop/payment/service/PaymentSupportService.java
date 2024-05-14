package com.t2m.g2nee.shop.payment.service;

import com.t2m.g2nee.shop.orderset.order.domain.Order;
import com.t2m.g2nee.shop.orderset.order.service.OrderService;
import com.t2m.g2nee.shop.payment.domain.Payment;
import com.t2m.g2nee.shop.payment.repository.PaymentRepository;
import com.t2m.g2nee.shop.point.service.PointService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PaymentSupportService {

    private final PointService pointService;
    private final OrderService orderService;
    private final PaymentRepository paymentRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void memberPoint(Long customerId, Order order, int point) {
        //포인트 사용
        if (point > 0) {
            pointService.usePoint(customerId, order.getOrderId(), point);
        }
        //포인트 적립
        pointService.givePurchasePoint(customerId, order);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void memberCoupon(Order order) {
        //쿠폰 사용으로 변경
        orderService.applyUseCoupon(order);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Payment memberPayment(Long orderId, Payment payment) {
        //order상태 변경
        orderService.changeOrderState(orderId, Order.OrderState.WAITING);
        //결제 테이블 저장
        return paymentRepository.save(payment);
    }
}
