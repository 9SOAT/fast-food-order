package com.fiap.challenge.order.domain.model.exception;


import static org.springframework.http.HttpStatus.NOT_FOUND;


public class NotFoundException extends BusinessException {

    public NotFoundException(String message, String code) {
        super(NOT_FOUND, message, code);
    }
}
