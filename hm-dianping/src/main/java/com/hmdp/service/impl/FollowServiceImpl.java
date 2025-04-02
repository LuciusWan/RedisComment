package com.hmdp.service.impl;

import com.hmdp.dto.Result;
import com.hmdp.dto.UserDTO;
import com.hmdp.entity.Follow;
import com.hmdp.entity.User;
import com.hmdp.mapper.FollowMapper;
import com.hmdp.mapper.UserMapper;
import com.hmdp.service.IFollowService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hmdp.utils.RedisConstants;
import com.hmdp.utils.UserHolder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 虎哥
 * @since 2021-12-22
 */
@Service
public class FollowServiceImpl extends ServiceImpl<FollowMapper, Follow> implements IFollowService {
    private static final Logger logger = LoggerFactory.getLogger(FollowServiceImpl.class);
    @Autowired
    StringRedisTemplate stringRedisTemplate;
    @Autowired
    private FollowMapper followMapper;
    @Autowired
    private UserMapper userMapper;

    public Result followServer(Long bloggerId, Boolean isFollow) {
        Long userId= UserHolder.getUser().getId();
        if(isFollow){
            Follow follow=new Follow();
            follow.setUserId(userId);
            follow.setFollowUserId(bloggerId);
            follow.setCreateTime(LocalDateTime.now());
            followMapper.insert(follow);
            stringRedisTemplate.opsForSet().add(RedisConstants.FOLLOW+userId.toString(),bloggerId+"");
            logger.info("id为"+userId+"的用户关注了id为"+bloggerId+"的用户");
        }else if(!isFollow){
            followMapper.deleteByUserId(userId,bloggerId);
            logger.info("id为"+userId+"取消关注了id为"+bloggerId+"的用户");
            stringRedisTemplate.opsForSet().remove(RedisConstants.FOLLOW+userId.toString(),bloggerId+"");

        }
        return Result.ok();
    }

    @Override
    public Result followOrNot(Long id) {
        Long userId= UserHolder.getUser().getId();
        Follow follow= followMapper.followOrNot(userId,id);
        if(follow!=null){
            return Result.ok(true);
        }else {
            return Result.ok(false);
        }
    }

    @Override
    public Result followCommon(Long id) {
        System.out.println(id);
        Long userId= UserHolder.getUser().getId();
        System.out.println(userId);
        List<User> list=new ArrayList<>();
        Set<String> intersect = stringRedisTemplate.opsForSet().intersect(RedisConstants.FOLLOW + userId.toString() , RedisConstants.FOLLOW+id.toString());
        System.out.println(intersect);
        if(intersect==null||intersect.size()==0){
            return Result.ok();
        }
        for (String interId : intersect) {
            list.add(userMapper.selectById(Long.valueOf(interId)));
        }
        List<UserDTO> userDTOS=new ArrayList<>();
        for (User user : list) {
            UserDTO userDTO=new UserDTO();
            BeanUtils.copyProperties(user,userDTO);
            userDTOS.add(userDTO);
        }
        return Result.ok(userDTOS);

    }
}
