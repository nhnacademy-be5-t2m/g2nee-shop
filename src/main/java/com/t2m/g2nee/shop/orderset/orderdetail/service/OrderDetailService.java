package com.t2m.g2nee.shop.orderset.orderdetail.service;

import com.t2m.g2nee.shop.orderset.order.domain.Order;
import com.t2m.g2nee.shop.orderset.orderdetail.dto.request.OrderDetailSaveDto;
import com.t2m.g2nee.shop.orderset.orderdetail.dto.response.GetOrderDetailResponseDto;
import java.util.List;

/**
 * 도서별 주문 상세 서비스 인터페이스
 *
 * @author : 박재희
 * @since : 1.0
 */
public interface OrderDetailService {


    /**
     * 도서 주문의 상세 내역 조회
     *
     * @
     * @
     */
    List<GetOrderDetailResponseDto> getOrderDetailListByOrderId(Long orderId);

    /**
     * 주문 상세를 기반으로 주문 이름 생성합니다.
     *
     * @param orderId 주문 id
     * @return 주문 이름
     * @author : 김수빈
     * @since : 1.0
     */
    String getOrderName(Long orderId);

    /**
     * 주문 상세 취소 상태 변경
     *
     * @param orderDetailId
     */
    void changeOrderDetailIsCancelled(Long orderDetailId);

    /**
     * 주문 상세 삭제
     *
     * @param orderDetailId
     */
    void deleteOrderDetail(Long orderDetailId);


    /**
     * 주문 상세를 저장합니다.
     *
     * @param order           주문서
     * @param orderDetailList 주문 상세 리스트
     * @return 주문 상세 리스트 목록
     * @author : 김수빈
     * @since : 1.0
     */
    List<GetOrderDetailResponseDto> saveOrderDetails(Order order, List<OrderDetailSaveDto> orderDetailList);

    /**
     * 주문에 해당하는 주문 상세를 취소합니다.
     * 배송 전 결제 취소에 대한 주문 취소를 위한 메소드 입니다.
     *
     * @param orderId 주문 id
     * @author : 김수빈
     * @since : 1.0
     */
    void cancelAllOrderDetail(Long orderId);


    /**
     * 주문 id에 따른 주문 상세의
     * 결제 성공 시 재고를 줄이기 위해 사용합니다.
     *
     * @param orderId 주문 id
     * @author : 김수빈
     * @since : 1.0
     */
    void setBookQuantity(Long orderId);

    /**
     * 결제 완료 시 쿠폰 취소
     */
    List<Order> applyUseCoupon(Order order);
}
