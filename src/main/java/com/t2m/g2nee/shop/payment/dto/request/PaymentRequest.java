package com.t2m.g2nee.shop.payment.dto.request;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import java.math.BigDecimal;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 결제요청을 다양하게 받기 위한 추상클래스 입니다.
 *
 * @author : 김수빈
 * @since : 1.0
 */
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME, //클래스 역렬화 방법: 클래스 이름
        property = "payType", //역직렬화 시 사용하는 타입
        visible = true //true 일 경우 property에 있는 값도 역직렬화
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = TossPaymentRequestDto.class, name = "toss")//역직렬화 할 수 있는 하위 클래스
})
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public abstract class PaymentRequest {
    /**
     * 주문 번호
     */
    @NotBlank
    private String orderNumber;

    /**
     * 결제 금액
     */
    @NotNull
    private BigDecimal amount;

    /**
     * 고객 id
     */
    @NotNull
    private Long customerId;

    /**
     * 결제 종류
     */
    @NotBlank
    private String payType;

    /**
     * 사용 포인트
     */
    private int point;
}