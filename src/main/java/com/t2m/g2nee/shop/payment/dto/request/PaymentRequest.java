package com.t2m.g2nee.shop.payment.dto.request;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type" // JSON에서 타입을 나타내는 필드 이름
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = TossPaymentRequestDto.class, name = "toss")
})
public interface PaymentRequest {
    String getPayType();
}
