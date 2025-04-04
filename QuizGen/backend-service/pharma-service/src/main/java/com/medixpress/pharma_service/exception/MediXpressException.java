package com.medixpress.pharma_service.exception;

import lombok.Data;

@Data
public class MediXpressException extends RuntimeException {

    private int httpStatusCode;
    public MediXpressException(String message,int httpStatusCode) {
        super(message);
        this.httpStatusCode =httpStatusCode;
    }
}
