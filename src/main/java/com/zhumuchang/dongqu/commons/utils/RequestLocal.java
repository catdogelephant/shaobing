package com.zhumuchang.dongqu.commons.utils;

import com.zhumuchang.dongqu.commons.interceptor.TokenUser;

/**
 * @Author sx
 * @Description 当前登录用户信息
 * @Date 2022/8/9 11:49
 */
public class RequestLocal {

    /**
     * 用户ID
     */
    private static ThreadLocal<String> tokenUserIdLocal = new ThreadLocal<>();

    /**
     * 用户名
     */
    private static ThreadLocal<String> tokenUserNameLocal = new ThreadLocal<>();
    /**
     * 用户名
     */
    private static ThreadLocal<TokenUser> tokenUserLocal = new ThreadLocal<>();


    /**
     * 用户ID
     */
    public static String getTokenUserId() {
        return tokenUserIdLocal.get();
    }

    public static void setTokenUserId(String uid) {
        tokenUserIdLocal.set(uid);
    }

    public static void removeTokenUserId() {
        tokenUserIdLocal.remove();
    }

    /**
     * 用户名
     */
    public static String getTokenUserName() {
        return tokenUserNameLocal.get();
    }

    public static void setTokenUserName(String userName) {
        tokenUserNameLocal.set(userName);
    }

    public static void removeTokenUserName() {
        tokenUserNameLocal.remove();
    }

    public static TokenUser getTokenUser() {
        return tokenUserLocal.get();
    }

    public static void setTokenUser(TokenUser tokenUser) {
        tokenUserLocal.set(tokenUser);
    }

    public static void removeTokenUser() {
        tokenUserLocal.remove();
    }

}
