package com.example.ext.service.enums.error;

import com.example.common.core.enums.exception.ErrorCode;

public enum ExtErrorCode implements ErrorCode {
    INVALID_REQUEST_CREATE_ROOM("1", "request is not null"),
    INVALID_REQUEST_GET_ROOM_ID("2", "id is not null"),
    ROOM_IS_NOT_EXIST("3", "Entity is not exist"),
    ;

    private String code;
    private String desc;

    private ExtErrorCode(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return this.desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getServicePrefix() {
        return "CC";
    }
}
