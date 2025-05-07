package com.fiap.challenge.order.domain.model.exception;


import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;


public class UnprocessableEntityException extends BusinessException {

    public UnprocessableEntityException(String message, String code) {
        super(UNPROCESSABLE_ENTITY, message, code);
    }
}
