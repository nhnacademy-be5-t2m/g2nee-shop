package com.t2m.g2nee.shop.policyset.pointPolicy.dto.annotation;

import com.t2m.g2nee.shop.policyset.pointPolicy.domain.PointPolicy;
import com.t2m.g2nee.shop.policyset.pointPolicy.dto.request.PointPolicySaveDto;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PolicyTypeValidator implements ConstraintValidator<PolicyType, PointPolicySaveDto> {

    @Override
    public boolean isValid(PointPolicySaveDto pointPolicySaveDto,
                           ConstraintValidatorContext constraintValidatorContext) {
        if (pointPolicySaveDto.getPolicyType().equals(PointPolicy.PolicyType.PERCENT.toString())) {
            return pointPolicySaveDto.getAmount().toString().matches("^(0.)\\d*[^0]$\n");

        } else if (pointPolicySaveDto.getPolicyType().equals(PointPolicy.PolicyType.AMOUNT.toString())) {
            return pointPolicySaveDto.getAmount().toString().matches("^[^0]\\d*\n");
        }

        return false;
    }
}
