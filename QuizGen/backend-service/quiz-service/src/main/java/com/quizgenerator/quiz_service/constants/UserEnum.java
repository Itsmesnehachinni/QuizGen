package com.quizgenerator.quiz_service.constants;

public enum UserEnum {
    CUSTOMER("CUSTOMER"),
    PHARMA_ADMIN("PHARMA_ADMIN");
    private String value;
    UserEnum(String value) {
        this.value = value;
    }
}
