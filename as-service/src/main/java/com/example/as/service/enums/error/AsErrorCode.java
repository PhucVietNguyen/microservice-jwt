package com.example.as.service.enums.error;

import com.example.common.core.enums.exception.ErrorCode;

public enum AsErrorCode implements ErrorCode {

    USER_IS_NOT_EXIST("1", "user is not exist")
    ;

    private String code;
    private String desc;

    private AsErrorCode(String code, String desc) {
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
