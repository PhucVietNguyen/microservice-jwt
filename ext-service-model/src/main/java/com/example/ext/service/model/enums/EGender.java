package com.example.ext.service.model.enums;

public enum EGender {

    MALE("M"),
    FEMALE("F"),
    OTHER("O");

    EGender(String code) {
        this.code = code;
    }

    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
