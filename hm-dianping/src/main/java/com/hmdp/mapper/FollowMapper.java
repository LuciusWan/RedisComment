package com.hmdp.mapper;

import com.hmdp.entity.Follow;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 虎哥
 * @since 2021-12-22
 */
public interface FollowMapper extends BaseMapper<Follow> {
    @Delete("delete from hmdp.tb_follow where hmdp.tb_follow.follow_user_id=#{bloggerId} and hmdp.tb_follow.user_id=#{userId}")
    void deleteByUserId(Long userId, Long bloggerId);
    @Select("select * from hmdp.tb_follow where user_id=#{userId} and follow_user_id=#{id}")
    Follow followOrNot(Long userId, Long id);
}
