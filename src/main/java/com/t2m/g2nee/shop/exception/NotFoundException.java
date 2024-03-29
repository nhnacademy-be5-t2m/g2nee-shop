package com.t2m.g2nee.shop.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * 404 NotFound Exception을 다루는 클래스 입니다. 예외를 던질 때 코드와 메시지를 초기화 해주세요
 *
 * @author : 신동민
 * @since : 1.0
 */
@Getter
public class NotFoundException extends CustomException {

    public NotFoundException(String message) {
        super(HttpStatus.NOT_FOUND, message);
    }
}
