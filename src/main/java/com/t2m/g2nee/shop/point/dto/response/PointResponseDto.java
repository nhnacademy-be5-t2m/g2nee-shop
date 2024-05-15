package com.t2m.g2nee.shop.point.dto.response;

import com.t2m.g2nee.shop.point.domain.Point;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PointResponseDto {

    private int point;
    private LocalDateTime changeDate;
    private Point.ChangeReason reason;
}
