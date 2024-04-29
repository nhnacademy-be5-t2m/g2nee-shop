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

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "payType",
        visible = true
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = TossPaymentRequestDto.class, name = "toss")
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
}