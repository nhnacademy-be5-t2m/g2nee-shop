package com.t2m.g2nee.shop.orderset.orderdetail.service;

import com.t2m.g2nee.shop.orderset.orderdetail.dto.response.GetOrderDetailResponseDto;
import java.util.List;

/**
 * 도서별 주문 상세 서비스 인터페이스
 *
 * @author : 박재희
 * @since : 1.0
 */
public interface OrderDetailService {
//    /**
//     * 도서 주문 생성
//     *
//     * @param createRequestDto dto 객체
//     * @param order            해당 도서를 주문하는 전체 주문
//     */
//    Long createOrderDetail(OrderDetailCreateRequestDto createRequestDto,
//                           Order order);
//
//    /**
//     * 도서 주문 상세 정보 조회
//     *
//     * @param orderDetailId
//     * @return 주문 상세 반환
//     */
//    GetOrderDetailResponseDto getOrderDetailById(Long orderDetailId);

    /**
     * 도서 주문의 상세 내역 조회
     *
     * @
     * @
     */
    List<GetOrderDetailResponseDto> getOrderDetailListByOrderId(Long orderId);

    /**
     * 주문 상세를 기반으로 주문 이름 생성
     *
     * @param orderId 주문 id
     * @return 주문 이름
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
}
