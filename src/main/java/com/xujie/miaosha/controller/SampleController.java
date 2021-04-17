package com.xujie.miaosha.controller;

import com.xujie.miaosha.domain.User;
import com.xujie.miaosha.redis.KeyPrefix;
import com.xujie.miaosha.redis.UserKey;
import com.xujie.miaosha.result.CodeMsg;
import com.xujie.miaosha.result.Result;
import com.xujie.miaosha.services.RedisService;
import com.xujie.miaosha.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
@RequestMapping("/demo")
public class SampleController {

    @Resource
    private UserService userService;

    @Resource
    private RedisService redisService;

    @RequestMapping("/db/get")
    @ResponseBody
    public Result<User> dbGet( int id ) {
        User user = userService.getById(id);
        return Result.success(user);
    }

    @RequestMapping("/db/tx")
    @ResponseBody
    public Result<Boolean> dbTx() {
        boolean tx = userService.tx();
        return Result.success(tx);
    }

    @RequestMapping("/redis/get")
    @ResponseBody
    public Result<User> redisGet() {
        User v1 = redisService.get(UserKey.getById, "" + 1, User.class);
        return Result.success(v1);
    }

    @RequestMapping("/redis/set")
    @ResponseBody
    public Result<Boolean> redisSet() {
        User user = new User(1, "1111");
        boolean set = redisService.set(UserKey.getById, "" + 1, user);
        return Result.success(set);
    }

}
