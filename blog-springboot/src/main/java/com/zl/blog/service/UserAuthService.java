package com.zl.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zl.blog.entity.UserAuth;
import com.zl.blog.pojo.vo.UserVO;

/**
 * 用户账号服务
 *
 * @author 冷血毒舌
 * @description 针对表【sys_user_auth】的数据库操作Service
 * @createDate 2022-11-15 23:02:46
 */
public interface UserAuthService extends IService<UserAuth> {

    /**
     * 根据用户名获取用户账户信息
     *
     * @param username
     * @return
     */
    UserAuth getUserAuthByUsername(String username);

    /**
     * 注册
     *
     * @param user
     */
    void register(UserVO user);

    /**
     * 登录
     *
     * @param user
     */
    String login(UserVO user);

    /**
     * 登出
     */
    void logout();
}
