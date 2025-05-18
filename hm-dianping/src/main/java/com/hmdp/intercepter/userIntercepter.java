package com.hmdp.intercepter;

import com.hmdp.utils.ThreadLocalUtils;
import com.hmdp.utils.UserHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.TimeUnit;
public class userIntercepter implements HandlerInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(userIntercepter.class);
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = ThreadLocalUtils.getToken();
        if(token==null){
            logger.info("未登录拦截请求");
            return false;
        }
        if(UserHolder.getUser()==null){
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }

        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        UserHolder.removeUser();
    }
}
