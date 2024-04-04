package com.t2m.g2nee.shop.exception;

import org.springframework.http.HttpStatus;

public class AlreadyExistException extends CustomException {
    public AlreadyExistException(String message) {
        super(HttpStatus.CONFLICT, message);
    }
}
