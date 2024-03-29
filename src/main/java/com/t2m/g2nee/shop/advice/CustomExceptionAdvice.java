package com.t2m.g2nee.shop.advice;

import com.t2m.g2nee.shop.exception.CustomException;
import com.t2m.g2nee.shop.exception.ErrorResponse;
import javax.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
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

    /**
     * NotFoundException을 받는 Handler 메서드
     *
     * @param e custom excpetion 객체
     * @return 에러에 관한 정보가 있는 ErrorResponse 객체
     */
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundException(CustomException e) {

        ErrorResponse response = ErrorResponse.builder()
                .code(e.getCode())
                .message(e.getMessage())
                .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    /**
     * Pathvariable 예외를 다루는 handler
     *
     * @param e 예외 객체
     * @return 에러에 관한 정보가 있는 ErrorResponse 객체
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolationException(ConstraintViolationException e) {
        return null;
    }

    /**
     * RequestBody 예외를 다루는 hnadler
     *
     * @param e 예외 객체
     * @return 에러에 관한 정보가 있는 ErrorResponse 객체
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> MethodArgumentNotValidException(MethodArgumentNotValidException e) {
        return null;
    }

}
