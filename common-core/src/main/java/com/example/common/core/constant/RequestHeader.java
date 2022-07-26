package com.example.common.core.constant;

import javax.servlet.http.HttpServletRequest;

public class RequestHeader {

    public static final String CLIENT_ID = "client-id";
    public static final String USER_ID = "user-id";

    public RequestHeader() {
    }

    public static String getUserId(HttpServletRequest request) {
        return (String)request.getAttribute("user-id");
    }

    public static String getClientId(HttpServletRequest request) {
        return (String)request.getAttribute("client-id");
    }
}
