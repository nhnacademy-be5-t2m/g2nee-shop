package com.t2m.g2nee.shop.policyset.pointpolicy.dto.annotation;

import com.t2m.g2nee.shop.policyset.pointpolicy.domain.PointPolicy;
import com.t2m.g2nee.shop.policyset.pointpolicy.dto.request.PointPolicySaveDto;
import java.math.BigDecimal;
import java.util.Objects;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 포인트 정책 저장 시, 포인트 타입에 맞는 적립 수치를 입력했는지 검증합니다.
 *
 * @author : 김수빈
 * @since : 1.0
 */
public class PolicyTypeValidator implements ConstraintValidator<PolicyType, PointPolicySaveDto> {

    /**
     * PolicyType에 따라 amount의 유효성을 검사하는 validator입니다.
     * @param pointPolicySaveDto 포인트 정책 저장 dto
     * @param constraintValidatorContext 유효성 검사 객체
     * @return 유효 시 true
     */
    @Override
    public boolean isValid(PointPolicySaveDto pointPolicySaveDto,
                           ConstraintValidatorContext constraintValidatorContext) {
        BigDecimal amount = pointPolicySaveDto.getAmount();
        String type = pointPolicySaveDto.getPolicyType();

        if (Objects.nonNull(amount) && Objects.nonNull(type)) {
            if (type.equals(PointPolicy.PolicyType.PERCENT.toString())) {
                //퍼센트 적립의 경우 0~1사이 값만 입력할 수 있게 함
                return amount.compareTo(BigDecimal.ZERO) > 0 && amount.compareTo(BigDecimal.ONE) <= 0;

            } else if (type.equals(PointPolicy.PolicyType.AMOUNT.toString())) {
                //금액 적립의 경우 0 초과 값을 입력하게 함
                return amount.stripTrailingZeros().scale() <= 0;
            }
        }

        return false;
    }
}
