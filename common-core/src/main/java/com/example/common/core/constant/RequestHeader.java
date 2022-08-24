package com.example.common.core.constant;

import javax.servlet.http.HttpServletRequest;

public class RequestHeader {

    public static final String CLIENT_ID = "client-id";

    public static final String USER_ID = "user-id";

    public static String getUserId(HttpServletRequest request) {
        return (String) request.getAttribute(USER_ID);
    }

    public static String getClientId(HttpServletRequest request) {
        return (String) request.getAttribute(CLIENT_ID);
    }
}
