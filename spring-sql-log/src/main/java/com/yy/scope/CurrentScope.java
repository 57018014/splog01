package com.yy.scope;

import com.github.phantomthief.scope.ScopeKey;
import com.yy.bean.User;
import org.springframework.util.StringUtils;


public class CurrentScope {

    private static final ScopeKey<Long> LOGIN_USER_ID = ScopeKey.withDefaultValue(0L);

    private static final ScopeKey<String> LOGIN_USER_TOKEN = ScopeKey.withDefaultValue("");

    private static final ScopeKey<User> LOGIN_USER = ScopeKey.withInitializer(() -> null);


    private static final ScopeKey<Long> LOGIN_TOKEN_EXPIRES = ScopeKey.withDefaultValue(Long.MAX_VALUE);


    private static final ScopeKey<String> OS = ScopeKey.withDefaultValue("");


    private CurrentScope() {
        throw new UnsupportedOperationException();
    }


    public static long getLoginUserId() {
        return LOGIN_USER_ID.get();
    }

    public static void setLoginUserId(long loginUserId) {
        LOGIN_USER_ID.set(loginUserId);
    }


    public static long getLoginTokenExpires() {
        return LOGIN_TOKEN_EXPIRES.get();
    }

    public static void setLoginTokenExpires(long expires) {
        LOGIN_TOKEN_EXPIRES.set(expires);
    }




    public static User getLoginUser() {
        return LOGIN_USER.get();
    }

    public static void setLoginUser(User userDO) {
        if (userDO == null) {
            throw new IllegalArgumentException();
        }
        LOGIN_USER.set(userDO);
    }

    public static ScopeKey<String> getLoginUserToken() {
        return LOGIN_USER_TOKEN;
    }
    public static String getLoginUserTokenString() {
        return LOGIN_USER_TOKEN.get();
    }

    public static void setLoginUserToken(String token) {
        if (StringUtils.hasText(token)) {
            throw new IllegalArgumentException();
        }
        LOGIN_USER_TOKEN.set(token);
    }

    public static String getOS() {
        return OS.get();
    }

    public static void setOS(String os) {
        OS.set(os);
    }


}
