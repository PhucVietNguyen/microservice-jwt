package com.example.as.service.model.constant;

public class AuthConstant {

    public static final String BASE_PATH = "/api/auth/";

    public static final String[] AUTH_WHITELIST = {
            BASE_PATH + "sign-in", BASE_PATH + "sign-up", BASE_PATH + "refresh-token",
            BASE_PATH + "sign-out"
    };

    public static final String[] DOCUMENT_WHITELIST = {
           //Todo add swagger
    };
}
