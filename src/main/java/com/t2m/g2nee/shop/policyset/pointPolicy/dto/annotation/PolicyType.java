package com.t2m.g2nee.shop.policyset.pointPolicy.dto.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;

/**
 * 포인트 정책 저장 시, 포인트 타입에 맞는 적립 수치를 입력했는지 검증하는 커스텀 어노테이션입니다.
 *
 * @author : 김수빈
 * @since : 1.0
 */
@Constraint(validatedBy = PolicyTypeValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface PolicyType {

    String message() default "잘못된 숫자 형식입니다. 올바른 값을 입력해 주세요";

    Class[] groups() default {};

    Class[] payload() default {};

}
