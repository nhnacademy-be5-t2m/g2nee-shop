package com.t2m.g2nee.shop.policyset.pointPolicy.dto.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;

@Constraint(validatedBy = PolicyTypeValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface PolicyType {
    String message() default "잘못된 숫자 형식입니다. 타입에 맞는 값을 입력해 주세요";

    Class[] groups() default {};

    Class[] payload() default {};
}
