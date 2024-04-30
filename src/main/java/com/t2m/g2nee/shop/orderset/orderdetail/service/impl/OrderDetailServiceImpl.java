package com.t2m.g2nee.shop.orderset.orderdetail.service.impl;

import com.t2m.g2nee.shop.exception.NotFoundException;
import com.t2m.g2nee.shop.memberset.Customer.repository.CustomerRepository;
import com.t2m.g2nee.shop.orderset.orderdetail.domain.OrderDetail;
import com.t2m.g2nee.shop.orderset.orderdetail.dto.response.GetOrderDetailResponseDto;
import com.t2m.g2nee.shop.orderset.orderdetail.repository.OrderDetailRepository;
import com.t2m.g2nee.shop.orderset.orderdetail.service.OrderDetailService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderDetailServiceImpl implements OrderDetailService {

    private final OrderDetailRepository orderDetailRepository;
    private final CustomerRepository customerRepository;


    //
//    @Override
//    @Transactional
//    public Long createOrderDetail(OrderDetailCreateRequestDto createRequestDto, Order order) {
//        return null;
//    }
//
//    @Override
//    public GetOrderDetailResponseDto getOrderDetailById(Long orderDetailId) {
//        GetOrderDetailResponseDto orderDetailResponseDto = orderDetailRepository.getOrderDetailById(orderDetailId)
//                .orElseThrow(() -> new NotFoundException("존재하지 않습니다."));
//        return orderDetailResponseDto;
//    }
    @Override
    @Transactional(readOnly = true)
    public List<GetOrderDetailResponseDto> getOrderDetailListByOrderId(Long orderId) {
        return orderDetailRepository.getOrderDetailListByOrderId(orderId);
    }

    @Override
    @Transactional(readOnly = true)
    public String getOrderName(Long orderId) {
        return null;
    }

    @Override
    @Transactional
    public void changeOrderDetailIsCancelled(Long orderDetailId) {
        OrderDetail orderDetail = orderDetailRepository.findById(orderDetailId)
                .orElseThrow(() -> new NotFoundException("주문 상세가 존재하지 않습니다."));
        Boolean isCancelled = !orderDetail.getIsCancelled();
        orderDetail.setIsCancelled(isCancelled);
        orderDetailRepository.save(orderDetail);

    }

    @Override
    public void deleteOrderDetail(Long orderDetailId) {
        orderDetailRepository.deleteById(orderDetailId);
    }


}
