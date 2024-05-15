package com.t2m.g2nee.shop.payment.dto.response;


import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 결제 정보를 응답하기 위한 dto입니다.
 *
 * @author : 김수빈
 * @since : 1.0
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class PaymentInfoDto {

    /**
     * 결제 id
     */
    private Long paymentId;

    /**
     * 결제 금액
     */
    private BigDecimal amount;

    /**
     * 결제 수단
     */
    private String payType;

    /**
     * 결제 날짜
     */
    private String paymentDate;

    /**
     * 결제 상태
     */
    private String payStatus;

    /**
     * 주문 id
     */
    private Long orderId;

    /**
     * 주문번호
     */
    private String orderNumber;

    /**
     * {책이름} 외 n개 형식의 주문 이름
     */
    private String orderName;


}
