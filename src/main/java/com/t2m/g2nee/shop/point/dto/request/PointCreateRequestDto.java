package com.t2m.g2nee.shop.point.dto.request;

import com.t2m.g2nee.shop.point.domain.Point;
import javax.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PointCreateRequestDto {
    @NotEmpty(message = "적립 대상 회원의 id를 입력해 주십시오.")
    private final Long memberId;
    private final Long orderId;
    @NotEmpty(message = "적립할 포인트를 입력해 주십시오.")
    private final int point;
    @NotEmpty(message = "포인트의 변동사유를 입력해 주십시오.")
    private final Point.ChangeReason reason;
}
