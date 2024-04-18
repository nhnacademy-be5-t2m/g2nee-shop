package com.t2m.g2nee.shop.policyset.pointPolicy.dto.annotation;

import com.t2m.g2nee.shop.policyset.pointPolicy.domain.PointPolicy;
import com.t2m.g2nee.shop.policyset.pointPolicy.dto.request.PointPolicySaveDto;
import java.math.BigDecimal;
import java.util.Objects;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PolicyTypeValidator implements ConstraintValidator<PolicyType, PointPolicySaveDto> {

    @Override
    public boolean isValid(PointPolicySaveDto pointPolicySaveDto,
                           ConstraintValidatorContext constraintValidatorContext) {
        BigDecimal amount = pointPolicySaveDto.getAmount();
        String type = pointPolicySaveDto.getPolicyType();

        if (Objects.nonNull(amount) && Objects.nonNull(type)) {
            if (type.equals(PointPolicy.PolicyType.PERCENT.toString())) {
                return amount.compareTo(BigDecimal.ZERO) > 0 && amount.compareTo(BigDecimal.ONE) <= 0;

            } else if (type.equals(PointPolicy.PolicyType.AMOUNT.toString())) {
                return amount.stripTrailingZeros().scale() <= 0;
            }
        }

        return false;
    }
}
