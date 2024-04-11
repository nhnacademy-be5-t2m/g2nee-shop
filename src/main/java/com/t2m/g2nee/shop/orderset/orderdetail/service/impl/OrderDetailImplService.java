package com.t2m.g2nee.shop.orderset.orderdetail.service.impl;

import com.t2m.g2nee.shop.orderset.order.domain.Order;
import com.t2m.g2nee.shop.orderset.orderdetail.dto.request.OrderDetailCreateRequestDto;
import com.t2m.g2nee.shop.orderset.orderdetail.dto.response.GetOrderDetailResponseDto;
import com.t2m.g2nee.shop.orderset.orderdetail.service.OrderDetailService;

public class OrderDetailImplService implements OrderDetailService {
    @Override
    public Long createOrderDetail(OrderDetailCreateRequestDto createRequestDto, Order order) {
        return null;
    }

    @Override
    public GetOrderDetailResponseDto getOrderDetailById(Long orderDetailId) {
        return null;
    }
}
