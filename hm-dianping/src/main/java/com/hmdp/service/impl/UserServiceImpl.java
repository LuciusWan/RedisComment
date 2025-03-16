package com.hmdp.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hmdp.controller.UserController;
import com.hmdp.dto.LoginFormDTO;
import com.hmdp.dto.Result;
import com.hmdp.dto.UserDTO;
import com.hmdp.entity.User;
import com.hmdp.mapper.UserMapper;
import com.hmdp.service.IUserService;
import com.hmdp.tools.Log;
import com.hmdp.utils.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;


/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author 虎哥
 * @since 2021-12-22
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    private final UserMapper userMapper;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    public UserServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public Result sendCode(String phone, HttpSession session) {
        //验证手机号是否合法
        if(RegexUtils.isPhoneInvalid(phone)){
            return Result.fail("手机号格式错误");
        }
        //生成验证码
        String code= RandomUtil.randomString(6);
        stringRedisTemplate.opsForValue().set(RedisConstants.LOGIN_CODE_KEY+phone,code, RedisConstants.LOGIN_CODE_TTL, TimeUnit.MINUTES);
        logger.info("生成验证码{}",code);
        return Result.ok();
    }

    @Override
    public Result login(LoginFormDTO loginForm, HttpSession session) {
        //从loginFormDto里面获得电话号码和code
        String phone= loginForm.getPhone();
        //如果phone不对,就返回false
        if(RegexUtils.isPhoneInvalid(phone)){
            return Result.fail("手机号格式错误");
        }
        //如果code和session的code对的上就返回true
        System.out.println(loginForm.getCode());
        if(loginForm.getCode().equals(stringRedisTemplate.opsForValue().get(RedisConstants.LOGIN_CODE_KEY+phone))){
            //先查询是否有电话号码(用户是否存在)
            User user =userMapper.selectByPhone(phone);
            //不在数据库里面就存储phone,password
            if(user==null){
                user=new User();
                BeanUtils.copyProperties(loginForm,user);
                user.setCreateTime(LocalDateTime.now());
                user.setUpdateTime(LocalDateTime.now());
                userMapper.insert(user);
                logger.info("用户注册完成{}",user);
            }
            UserDTO userDTO=new UserDTO();
            BeanUtils.copyProperties(user,userDTO);
            String token= UUID.randomUUID().toString();
            Map<String,Object> map= BeanUtil.beanToMap(userDTO,new HashMap<>(),
                CopyOptions.create()
                        .setIgnoreNullValue(true)
                        .setFieldValueEditor((fieldName,fieldValue)->fieldValue.toString()));

            stringRedisTemplate.opsForHash().putAll(RedisConstants.LOGIN_USER_KEY+token,map);
            System.out.println(token);
            stringRedisTemplate.expire(token,RedisConstants.CACHE_SHOP_TTL, TimeUnit.MINUTES);
            return Result.ok(token);
        }else{
            return Result.fail("验证码错误");
        }
    }

    @Override
    public void logout() {
        String token=ThreadLocalUtils.getToken();
        System.out.println(token);
        stringRedisTemplate.delete(RedisConstants.LOGIN_USER_KEY+ThreadLocalUtils.getToken());

    }
}
