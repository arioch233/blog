package com.zl.blog.controller;

import com.zl.blog.common.Result;
import com.zl.blog.pojo.vo.UserVO;
import com.zl.blog.service.UserAuthService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * 用户账号控制器
 *
 * @author 冷血毒舌
 * @create 2022/11/16 13:40
 */
@Api(tags = "用户账号模块")
@RestController
@RequestMapping("/user")
public class UserAuthController {

    @Autowired
    private UserAuthService userAuthService;

    /**
     * 用户登录
     *
     * @param user 登录信息
     * @return {@link Result}
     */
    @ApiOperation(value = "用户登录")
    @PostMapping("/login")
    public Result login(@Valid @RequestBody UserVO user) {
        return Result.success(userAuthService.login(user), "登录成功");
    }


    /**
     * 用户注册
     *
     * @param user 用户信息
     * @return {@link Result}
     */
    @ApiOperation(value = "用户注册")
    @PostMapping("/register")
    public Result register(@Valid @RequestBody UserVO user) {
        userAuthService.register(user);
        return Result.success(user);
    }


    /**
     * 用户登出
     *
     * @return
     */
    @ApiOperation(value = "用户登出")
    @PostMapping("/logout")
    public Result logout() {
        userAuthService.logout();
        return Result.success("成功退出登录");
    }
}
