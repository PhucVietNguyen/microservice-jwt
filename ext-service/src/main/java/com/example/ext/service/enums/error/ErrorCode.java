package com.example.ext.service.enums.error;

public interface ErrorCode {
    String getCode();

    String getDesc();

    String getServicePrefix();

    default String getServiceErrorCode() {
        String var10000 = this.getServicePrefix();
        return var10000 + "_" + this.getCode();
    }
}
