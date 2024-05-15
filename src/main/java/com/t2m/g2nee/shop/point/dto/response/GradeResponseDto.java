package com.t2m.g2nee.shop.point.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class GradeResponseDto {
    private Long totalAmount;
    private String grade;
}
