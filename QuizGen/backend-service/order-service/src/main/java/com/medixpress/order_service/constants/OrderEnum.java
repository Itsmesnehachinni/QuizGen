package com.medixpress.order_service.constants;

public enum OrderEnum {
    CREATED("CREATED"),
    DISPATCHED("DISPATCHED"),
    DELIVERED("DELIVERED"),
    ORDERED("ORDERED");
    private String value;
    OrderEnum(String value) {
        this.value = value;
    }
}
