package com.t2m.g2nee.shop.exception;

import lombok.Builder;
import lombok.Getter;

/**
 * 에러 정보를 반환할 객체를 만드는 클래스 입니다.
 *
 * @author : 신동민
 * @since : 1.0
 */
@Getter
@Builder
public class ErrorResponse {

    private int code;
    private String message;

}
