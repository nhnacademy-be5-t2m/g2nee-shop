package com.t2m.g2nee.shop.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * 상위 Exception을 다루는 클래스 입니다. Exception 필요시 상속하여 사용해주세요
 *
 * @author : 신동민
 * @since : 1.0
 */
@Getter
public class CustomException extends RuntimeException {

    private final HttpStatus httpStatus;
    private final String message;

    public CustomException(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }
}
