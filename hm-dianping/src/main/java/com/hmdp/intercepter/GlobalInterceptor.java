package com.hmdp.intercepter;

import cn.hutool.core.bean.BeanUtil;
import com.hmdp.dto.UserDTO;
import com.hmdp.utils.RedisConstants;
import com.hmdp.utils.ThreadLocalUtils;
import com.hmdp.utils.UserHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class GlobalInterceptor implements HandlerInterceptor {
    private StringRedisTemplate stringRedisTemplate;
    public GlobalInterceptor(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate=stringRedisTemplate;
    }
    private static final Logger logger = LoggerFactory.getLogger(GlobalInterceptor.class);
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("authorization");
        ThreadLocalUtils.setToken(token);
        Map<Object,Object> map=stringRedisTemplate.opsForHash().entries(RedisConstants.LOGIN_USER_KEY +token);
        if(!map.isEmpty()){
            UserDTO userDTO= BeanUtil.fillBeanWithMap(map,new UserDTO(),false);
            UserHolder.saveUser(userDTO);
        }
        stringRedisTemplate.expire(RedisConstants.LOGIN_USER_KEY+token,RedisConstants.CACHE_SHOP_TTL, TimeUnit.MINUTES);
        return true;
    }


}
