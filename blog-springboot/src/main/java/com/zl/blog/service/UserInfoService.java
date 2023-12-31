package com.zl.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zl.blog.entity.UserInfo;
import com.zl.blog.pojo.vo.PasswordVO;
import com.zl.blog.pojo.vo.UserDisableVO;
import com.zl.blog.pojo.vo.UserInfoVO;
import com.zl.blog.pojo.vo.UserRoleVO;

/**
 * 用户信息服务
 *
 * @author 冷血毒舌
 * @description 针对表【sys_user_info】的数据库操作Service
 * @createDate 2022-11-15 23:02:55
 */
public interface UserInfoService extends IService<UserInfo> {

    /**
     * 更新用户信息
     *
     * @param userInfoVO
     */
    void updateUserInfo(UserInfoVO userInfoVO);

    /**
     * 修改账号密码
     *
     * @param passwordVO
     */
    void updateUserPassword(PasswordVO passwordVO);

    /**
     * 更改用户禁用状态
     *
     * @param userDisableVO
     */
    void updateUserDisable(UserDisableVO userDisableVO);

    /**
     * 更新用户角色
     *
     * @param userRoleVO
     */
    void updateUserRole(UserRoleVO userRoleVO);
}
