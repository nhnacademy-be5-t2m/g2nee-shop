package com.t2m.g2nee.shop.payment.dto.response;

import java.math.BigDecimal;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Toss Paymenr에서 응답을 받기 위한 dto입니다.
 * @author : 김수빈
 * @since : 1.0
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class TossPaymentResponseDto {

    /**
     * 페이먼트 키
     */
    private String paymentKey;
    /**
     * 주문 id(주문 번호 = orderNumber)
     */
    private String orderId;
    /**
     * 주문 이름
     */
    private String orderName;
    /**
     * 결제 수단: 카드, 상품권 등
     */
    private String method;
    /**
     * 결제 금액
     */
    private BigDecimal totalAmount;
    /**
     * 결제 상태: DONE, CANCELLED 등
     */
    private String status;
    /**
     * 결제 승인 날짜
     */
    private String approvedAt;
    /**
     * 결제 취소 시 객체
     */
    private List<Cancel> cancels;
    /**
     * 오류 시 객체
     */
    private Failure failure;

    /**
     * 결제 취소 시 객체
     */
    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    public static class Cancel {
        /**
         * 취소 금액
         */
        private BigDecimal cancelAmount;
        /**
         * 취소 이유
         */
        private String cancelReason;
        /**
         * 취소 날짜
         */
        private String canceledAt;
        /**
         * 취소 상태
         */
        private String cancelStatus;
    }

    /**
     * 오류 발생 시 객체
     */
    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    public static class Failure {
        /**
         * 오류 코드: toss 자체 오류 코드
         */
        private String code;
        /**
         * 오류 메시지
         */
        private String message;
    }
}
