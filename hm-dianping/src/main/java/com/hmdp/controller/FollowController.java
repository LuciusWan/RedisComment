package com.hmdp.controller;


import com.hmdp.dto.Result;
import com.hmdp.service.IFollowService;
import com.hmdp.service.impl.FollowServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 虎哥
 * @since 2021-12-22
 */
@RestController
@RequestMapping("/follow")
public class FollowController {
    @Autowired
    private IFollowService followService;
    @PutMapping("/{bloggerId}/{bool}")
    public Result followUser(@PathVariable Long bloggerId, @PathVariable Boolean bool) {
        return followService.followServer(bloggerId,bool);
    }
    @GetMapping("/or/not/{id}")
    public Result followOrNot(@PathVariable Long id) {
        return followService.followOrNot(id);
    }
    @GetMapping("/common/{id}")
    public Result followCommon(@PathVariable Long id) {
        return followService.followCommon(id);
    }
}
