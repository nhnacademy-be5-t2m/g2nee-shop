package com.t2m.g2nee.shop.advice;

import com.t2m.g2nee.shop.exception.CustomException;
import com.t2m.g2nee.shop.exception.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Exception들을 다룰 Advice 클래스입니다. 우리가 만든 CustomException은 상속 받아 한 곳에서
 * 처리할 수 있도록 해주시고 예외 종류마다 메서드를 추가적으로 만들어 주세요
 * ex) 404 NotFound, 400 BadRequest ...
 *
 * @author : 신동민
 * @since : 1.0
 */
@RestControllerAdvice
public class CustomExceptionAdvice {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundException(CustomException e) {

        ErrorResponse response = ErrorResponse.builder()
                .code(e.getCode())
                .message(e.getMessage())
                .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
}
