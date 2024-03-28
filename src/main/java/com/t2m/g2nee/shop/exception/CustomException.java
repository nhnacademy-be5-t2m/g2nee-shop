package com.t2m.g2nee.shop.exception;

import lombok.Getter;

/**
 * 상위 Exception을 다루는 클래스 입니다. Exception 필요시 상속하여 사용해주세요
 *
 * @author : 신동민
 * @since : 1.0
 */
@Getter
public class CustomException extends RuntimeException {

    private final int code;
    private final String message;

    public CustomException(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
