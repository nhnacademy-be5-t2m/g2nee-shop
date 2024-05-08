package com.t2m.g2nee.shop.orderset.orderdetail.service.impl;

import com.t2m.g2nee.shop.bookset.book.domain.Book;
import com.t2m.g2nee.shop.bookset.book.service.BookGetService;
import com.t2m.g2nee.shop.bookset.book.service.BookMgmtService;
import com.t2m.g2nee.shop.couponset.coupon.domain.Coupon;
import com.t2m.g2nee.shop.couponset.coupon.service.CouponService;
import com.t2m.g2nee.shop.exception.BadRequestException;
import com.t2m.g2nee.shop.exception.NotFoundException;
import com.t2m.g2nee.shop.orderset.order.domain.Order;
import com.t2m.g2nee.shop.orderset.orderdetail.domain.OrderDetail;
import com.t2m.g2nee.shop.orderset.orderdetail.dto.request.OrderDetailSaveDto;
import com.t2m.g2nee.shop.orderset.orderdetail.dto.response.GetOrderDetailResponseDto;
import com.t2m.g2nee.shop.orderset.orderdetail.repository.OrderDetailRepository;
import com.t2m.g2nee.shop.orderset.orderdetail.service.OrderDetailService;
import com.t2m.g2nee.shop.orderset.packagetype.service.PackageService;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderDetailServiceImpl implements OrderDetailService {

    private final OrderDetailRepository orderDetailRepository;
    private final BookMgmtService bookMgmtService;
    private final BookGetService bookGetService;
    private final PackageService packageService;
    private final CouponService couponService;

    @Override
    @Transactional(readOnly = true)
    public List<GetOrderDetailResponseDto> getOrderDetailListByOrderId(Long orderId) {
        return orderDetailRepository.getOrderDetailListByOrderId(orderId);
    }


    @Override
    @Transactional
    public void changeOrderDetailIsCancelled(Long orderDetailId) {
        OrderDetail orderDetail = orderDetailRepository.findById(orderDetailId)
                .orElseThrow(() -> new NotFoundException("주문 상세가 존재하지 않습니다."));
        //취소 상태 변경
        orderDetail.setIsCancelled();
        orderDetailRepository.save(orderDetail);

        //취소 분량만큼 책 수량 추가
        bookMgmtService.modifyQuantity(orderDetail.getBook().getBookId(), orderDetail.getQuantity());
    }

    @Override
    @Transactional
    public void deleteOrderDetail(Long orderDetailId) {
        orderDetailRepository.deleteById(orderDetailId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public List<GetOrderDetailResponseDto> saveOrderDetails(Order order, List<OrderDetailSaveDto> orderDetailList) {
        //주문 상세 리스트를 돌며 각 주문 상세 저장

        for (OrderDetailSaveDto orderDetailSaveDto : orderDetailList) {
            //책 수량 확인
            Book book = bookGetService.getBook(orderDetailSaveDto.getBookId());
            if ((book.getQuantity() - orderDetailSaveDto.getQuantity()) < 0) {
                throw new BadRequestException("책 수량이 부족합니다.");
            }

            //order에서 쿠폰을 사용했으면 detail에서 사용하지 못 하도록 함
            Coupon coupon = null;
            if (Objects.isNull(order.getCoupon()) && Objects.nonNull(orderDetailSaveDto.getCouponId())) {
                coupon = couponService.getCoupon(orderDetailSaveDto.getCouponId());
            }

            //주문 상세 저장
            orderDetailRepository.save(
                    OrderDetail.builder()
                            .price(BigDecimal.valueOf(orderDetailSaveDto.getPrice()))
                            .quantity(orderDetailSaveDto.getQuantity())
                            .isCancelled(false)
                            .order(order)
                            .book(book)
                            .packageType(packageService.getPackageType(orderDetailSaveDto.getPackageId()))
                            .coupon(coupon)
                            .build()
            );
        }

        //주문 상세 목록 반환
        return orderDetailRepository.findByOrder_OrderId(order.getOrderId())
                .stream().map(this::convertToGetOrderDetailResponseDto)
                .collect(Collectors.toList());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void cancelAllOrderDetail(Long orderId) {
        List<OrderDetail> orderDetailList = orderDetailRepository.findByOrder_OrderId(orderId);

        for (OrderDetail orderDetail : orderDetailList) {
            //주문 취소 상태로 변경
            changeOrderDetailIsCancelled(orderDetail.getOrderDetailId());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void setBookQuantity(Long orderId) {
        List<OrderDetail> orderDetails = orderDetailRepository.findByOrder_OrderId(orderId);
        //수량 감소
        for (OrderDetail orderDetail : orderDetails) {
            bookMgmtService.modifyQuantity(orderDetail.getBook().getBookId(), orderDetail.getQuantity() * (-1));
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public String getOrderName(Long orderId) {
        List<OrderDetail> orderDetails = orderDetailRepository.findByOrder_OrderId(orderId);
        String orderName = orderDetails.get(0).getBook().getTitle();
        if (orderDetails.size() > 1) {
            orderName = orderName + " 외 " + (orderDetails.size() - 1);
        }

        return orderName;
    }

    private GetOrderDetailResponseDto convertToGetOrderDetailResponseDto(OrderDetail orderDetail) {
        //쿠폰 처리
        String coupon = null;
        if (Objects.nonNull(orderDetail.getCoupon())) {
            coupon = orderDetail.getCoupon().getCouponType().getName();
        }

        return new GetOrderDetailResponseDto(
                orderDetail.getOrderDetailId(), orderDetail.getPrice(), orderDetail.getQuantity(),
                orderDetail.getIsCancelled(), orderDetail.getBook().getTitle(), orderDetail.getPackageType().getName(),
                coupon
        );
    }

}
