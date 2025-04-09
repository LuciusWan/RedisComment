package com.hmdp.service.impl;

import ch.qos.logback.classic.spi.EventArgUtil;
import cn.hutool.core.util.BooleanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hmdp.dto.Result;
import com.hmdp.dto.UserDTO;
import com.hmdp.entity.Blog;
import com.hmdp.entity.User;
import com.hmdp.mapper.BlogMapper;
import com.hmdp.mapper.UserMapper;
import com.hmdp.service.IBlogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hmdp.utils.RedisConstants;
import com.hmdp.utils.SystemConstants;
import com.hmdp.utils.UserHolder;
import jodd.util.StringUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
public class BlogServiceImpl extends ServiceImpl<BlogMapper, Blog> implements IBlogService {
    @Autowired
    private BlogMapper blogMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private BlogServiceImpl blogService;
    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Override
    public Result selectOne(Long id) {
        Blog blog;
        blog=blogMapper.selectById(id);
        Long userId=blog.getUserId();
        User user=userMapper.selectById(userId);
        blog.setIcon(user.getIcon());
        blog.setName(user.getNickName());
        Boolean blogLiked = isBlogLiked(id);
        if(blogLiked){
            blog.setIsLike(true);
        }
        return Result.ok(blog);
    }



    @Override
    public Result queryHotBlog(Integer current) {
        // 根据用户查询
        Page<Blog> page = blogService.query()
                .orderByDesc("liked")
                .page(new Page<>(current, SystemConstants.MAX_PAGE_SIZE));
        // 获取当前页数据
        List<Blog> records = page.getRecords();
        // 查询用户
        records.forEach(blog ->{
            Long userId = blog.getUserId();
            User user = userService.getById(userId);
            blog.setName(user.getNickName());
            blog.setIcon(user.getIcon());
            if(isBlogLiked(blog.getId())){
                blog.setIsLike(true);
            }
        });
        return Result.ok(records);
    }

    @Override
    public void updateLike(Long id) {
        Long userId= UserHolder.getUser().getId();
        //获取时间戳
        long timestamp = System.currentTimeMillis();
        //向ZSET中插入,看是否点过赞了
        Boolean success = stringRedisTemplate.opsForZSet().add(RedisConstants.BLOG_LIKED_KEY + id, userId.toString(), timestamp);
        if(!Boolean.TRUE.equals(success)){
            //点过了赞就无法插入,就取消点赞,点赞数减一,从点赞集合中移除用户
            boolean success1 =update().setSql("liked = liked - 1").eq("id", id).update();
            if(success1) {
                stringRedisTemplate.opsForZSet().remove(RedisConstants.BLOG_LIKED_KEY + id, userId.toString());
            }
        }else{
            //没点过赞就可以插入,点赞数量加一
            update().setSql("liked = liked + 1").eq("id", id).update();
        }
    }

    @Override
    public Result selectLike(Long id) {
        //从ZSET中取最早点赞的5个人
        Set<String> ids = stringRedisTemplate.opsForZSet().range(RedisConstants.BLOG_LIKED_KEY + id, 0, 4);
        if(ids==null||ids.size()==0){
            return Result.ok();
        }
        List<Long> list=new ArrayList<>();
        for (String userId : ids) {
            list.add(Long.parseLong(userId));
        }
        String strId= StrUtil.join(",",ids);
        List<User> users = userService.query()
                .in("id", ids)
                .last("order by field(id,"+strId+")")
                .list();
        List<UserDTO> userDTOS = new ArrayList<>();
        for (User user : users) {
            UserDTO userDTO = new UserDTO();
            //只返回icon,userId和userName,其实userName都可以不传
            BeanUtils.copyProperties(user, userDTO);
            userDTOS.add(userDTO);
        }
        return Result.ok(userDTOS);
    }

    @Override
    public Result saveBlog(Blog blog) {
        // 获取登录用户
        UserDTO user = UserHolder.getUser();
        blog.setUserId(user.getId());
        // 保存探店博文
        boolean saveSuccess = blogService.save(blog);
        if(!saveSuccess){
            return Result.fail("发布笔记失败");
        }
        List<Long> idList=userMapper.selectByFollower(user.getId());
        if(idList!=null&&!idList.isEmpty()){
            for(Long id:idList){
                stringRedisTemplate.opsForZSet().add(RedisConstants.FEED_KEY + id, blog.getId().toString(), System.currentTimeMillis());
            }
        }

        // 返回id
        return Result.ok(blog.getId());
    }

    private Boolean isBlogLiked(Long id) {
        UserDTO user=UserHolder.getUser();
        if(user==null){
            //用户未登录,无需查看是否登录
            return false;
        }
        Long userId=user.getId();
        Double score = stringRedisTemplate.opsForZSet().score(RedisConstants.BLOG_LIKED_KEY + id, userId+"");
        if(score!=null) {
            return true;
        }else {
            return false;
        }
    }
}