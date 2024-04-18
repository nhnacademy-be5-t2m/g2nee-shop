package com.t2m.g2nee.shop.policyset.pointPolicy.dto.request;

import com.t2m.g2nee.shop.policyset.pointPolicy.dto.annotation.PolicyType;
import java.math.BigDecimal;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@PolicyType
public class PointPolicySaveDto {

    @NotBlank(message = "포인트 정책 이름은 비울 수 없습니다.")
    @Size(min = 1, max = 50, message = "20자 미만으로 입력해 주세요.")
    private String policyName;

    @NotBlank(message = "정책 종류는 비울 수 없습니다.")
    private String policyType;


    private BigDecimal amount;
}