package com.t2m.g2nee.shop.properties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * TossPayment를 위해 필요한 secretKey를 받을 수 있게 하는 클래스입니다.
 *
 * @author : 김수빈
 * @since : 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TossPaymentProperties {
    private String secretKey;
}
