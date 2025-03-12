package com.hmdp.tools;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Log {
    public static final String INFO = time()+"  INFO " +Thread.currentThread().getId()+" : ";
    public static final String DEBUG = time()+"  DEBUG "+Thread.currentThread().getId()+" : ";
    public static final String ERROR = time()+"  ERROR "+ Thread.currentThread().getId()+" : ";
    private static String time(){
        // 获取当前时间
        LocalDateTime now = LocalDateTime.now();

        // 定义格式化模式
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

        // 格式化 LocalDateTime
        String formattedDate = now.format(formatter);
        return formattedDate;
    }


}
