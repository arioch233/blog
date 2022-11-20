package com.zl.blog.controller;

import com.zl.blog.common.Result;
import com.zl.blog.pojo.vo.PasswordVO;
import com.zl.blog.pojo.vo.UserInfoVO;
import com.zl.blog.service.UserInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 用户信息控制器
 *
 * @author 冷血毒舌
 * @create 2022/11/19 16:48
 */
@Api(tags = "用户信息模块")
@RestController
@RequestMapping("/admin/user")
public class UserInfoController {

    @Autowired
    private UserInfoService userInfoService;

    /**
     * 更新用户信息
     *
     * @param userInfoVO
     * @return
     */
    @ApiOperation(value = "更新用户信息")
    @PutMapping("/info")
    public Result updateUserInfo(@RequestBody UserInfoVO userInfoVO) {
        userInfoService.updateUserInfo(userInfoVO);
        return Result.success();
    }

    /**
     * 修改密码
     *
     * @param passwordVO
     * @return
     */
    @ApiOperation(value = "修改密码")
    @PutMapping("/password")
    public Result updateUserPassword(@Valid @RequestBody PasswordVO passwordVO) {
        userInfoService.updateUserPassword(passwordVO);
        return Result.success();
    }
}
