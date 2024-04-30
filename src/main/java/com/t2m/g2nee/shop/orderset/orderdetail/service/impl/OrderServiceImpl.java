package com.t2m.g2nee.shop.orderset.orderdetail.service.impl;

import com.t2m.g2nee.shop.orderset.orderdetail.domain.OrderDetail;
import com.t2m.g2nee.shop.orderset.orderdetail.repository.OrderDetailRepository;
import com.t2m.g2nee.shop.orderset.orderdetail.service.OrderDetailService;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderDetailService {

    private final OrderDetailRepository orderDetailRepository;

    public OrderServiceImpl(OrderDetailRepository orderDetailRepository) {
        this.orderDetailRepository = orderDetailRepository;
    }

    @Override
    public String getOrderName(Long orderId) {
        List<OrderDetail> orderDetails = orderDetailRepository.findByOrder_OrderId(orderId);
        String orderName = orderDetails.get(0).getBook().getTitle();
        if(orderDetails.size() > 1){
            orderName = orderName + " 외 "+(orderDetails.size()-1);
        }

        return orderName;
    }
}
