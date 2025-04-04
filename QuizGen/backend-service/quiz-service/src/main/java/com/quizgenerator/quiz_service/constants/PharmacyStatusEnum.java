package com.quizgenerator.quiz_service.constants;

public enum PharmacyStatusEnum {
    ACTIVE("ACTIVE"),
    INACTIVE("INACTIVE");
    private String value;
    PharmacyStatusEnum(String value) {
        this.value = value;
    }
}
