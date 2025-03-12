package com.hmdp.utils;

public class ThreadLocalUtils {

    public static ThreadLocal<String> threadLocal = new ThreadLocal<>();

    public static void setToken(String token) {
        threadLocal.set(token);
    }

    public static String getToken() {
        return threadLocal.get();
    }

    public static void removeCurrentId() {
        threadLocal.remove();
    }

}