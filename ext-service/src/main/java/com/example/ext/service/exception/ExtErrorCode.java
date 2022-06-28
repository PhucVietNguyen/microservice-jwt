package com.example.ext.service.exception;

import com.example.common.core.enums.exception.ErrorCode;

public enum ExtErrorCode implements ErrorCode {
    INVALID_REQUEST_CREATE_ROOM("1", "request is not null")
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
