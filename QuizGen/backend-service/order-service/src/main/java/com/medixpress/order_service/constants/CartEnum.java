package com.medixpress.order_service.constants;

public enum CartEnum {
    COMPLETED("COMPLETED"),
    INPROGRESS("INPROGRESS");
    private String value;
    CartEnum(String value) {
        this.value = value;
    }
}
