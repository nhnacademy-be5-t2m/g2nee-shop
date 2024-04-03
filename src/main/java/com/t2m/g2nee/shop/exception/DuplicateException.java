package com.t2m.g2nee.shop.exception;

import org.springframework.http.HttpStatus;

/**
 * 정보가 중복되었을때 사용할 exception입니다. 어떤정보가 중복되어있는지 message에 작성해주세요.
 *
 * @author : 정지은
 * @since : 1.0
 */
public class DuplicateException extends CustomException {
    public DuplicateException(String message) {
        super(HttpStatus.CONFLICT, message);
    }
}
