package com.hmdp.mapper;

import com.hmdp.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 虎哥
 * @since 2021-12-22
 */
public interface UserMapper extends BaseMapper<User> {
    @Select("select * from hmdp.tb_user where phone=#{phone}")
    User selectByPhone(String phone);
    @Select("select user_id from hmdp.tb_follow where follow_user_id =#{id}")
    List<Long> selectByFollower(Long id);
}
