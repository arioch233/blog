package com.zl.blog.controller;

import com.zl.blog.common.Result;
import com.zl.blog.pojo.vo.*;
import com.zl.blog.service.UserAuthService;
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

    @Autowired
    private UserAuthService userAuthService;

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

    /**
     * 查询用户列表
     *
     * @param conditionVO
     * @return
     */
    @ApiOperation(value = "查询用户列表")
    @GetMapping("/all")
    public Result listUsers(ConditionVO conditionVO) {
        return Result.success(userAuthService.listUsers(conditionVO));
    }

    /**
     * 修改用户禁用状态
     *
     * @param userDisableVO 用户禁用信息
     * @return {@link Result}
     */
    @ApiOperation(value = "修改用户禁用状态")
    @PutMapping("/disable")
    public Result updateUserDisable(@Valid @RequestBody UserDisableVO userDisableVO) {
        userInfoService.updateUserDisable(userDisableVO);
        return Result.success();
    }

    /**
     * 修改用户角色
     *
     * @param userRoleVO 用户角色信息
     * @return {@link Result}
     */
    @ApiOperation(value = "修改用户角色")
    @PutMapping("/role")
    public Result updateUserRole(@Valid @RequestBody UserRoleVO userRoleVO) {
        userInfoService.updateUserRole(userRoleVO);
        return Result.success();
    }
}
