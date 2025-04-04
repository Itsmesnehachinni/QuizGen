package com.medixpress.order_service.constants;

public enum PaymentEnum {
    SUCCESSFUL("SUCCESSFUL"),
    FAILED("FAILED"),
    IN_PROGRESS("IN_PROGRESS");
    private String value;
    PaymentEnum(String value) {
        this.value = value;
    }
}
