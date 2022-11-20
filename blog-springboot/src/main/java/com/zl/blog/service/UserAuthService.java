package com.zl.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zl.blog.common.PageResult;
import com.zl.blog.entity.UserAuth;
import com.zl.blog.pojo.dto.UserBackDTO;
import com.zl.blog.pojo.dto.UserInfoDTO;
import com.zl.blog.pojo.vo.ConditionVO;
import com.zl.blog.pojo.vo.UserVO;

import javax.servlet.http.HttpServletRequest;

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
    UserInfoDTO login(UserVO user, HttpServletRequest request);

    /**
     * 登出
     */
    void logout();

    /**
     * 查询后台用户列表
     *
     * @param condition 条件
     * @return 用户列表
     */
    PageResult<UserBackDTO> listUsers(ConditionVO condition);
}
