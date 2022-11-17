package com.zl.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zl.blog.common.enums.LoginTypeEnum;
import com.zl.blog.common.enums.RoleEnum;
import com.zl.blog.config.shiro.token.JwtProvider;
import com.zl.blog.entity.UserAuth;
import com.zl.blog.entity.UserInfo;
import com.zl.blog.entity.UserRole;
import com.zl.blog.exception.ServiceException;
import com.zl.blog.mapper.UserAuthMapper;
import com.zl.blog.mapper.UserInfoMapper;
import com.zl.blog.mapper.UserRoleMapper;
import com.zl.blog.pojo.vo.UserVO;
import com.zl.blog.service.*;
import com.zl.blog.utils.ShiroUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Objects;

import static com.zl.blog.common.CommonConst.DEFAULT_NICKNAME;
import static com.zl.blog.common.RedisPrefixConst.USER_TOKEN;
import static com.zl.blog.common.enums.StatusCodeEnum.USERNAME_NOT_EXIST;

/**
 * 用户账号服务实现
 *
 * @author 冷血毒舌
 * @description 针对表【sys_user_auth】的数据库操作Service实现
 * @createDate 2022-11-15 23:02:46
 */
@Service
public class UserAuthServiceImpl extends ServiceImpl<UserAuthMapper, UserAuth>
        implements UserAuthService {


    @Resource
    private JwtProvider jwtProvider;

    @Resource
    private UserAuthMapper userAuthMapper;
    @Autowired
    private RedisService redisService;
    @Autowired
    private WebsiteConfigService websiteConfigService;

    @Autowired
    private UserInfoService userInfoService;
    @Resource
    private UserInfoMapper userInfoMapper;

    @Autowired
    private UserRoleService userRoleService;
    @Resource
    private UserRoleMapper userRoleMapper;

    @Override
    public UserAuth getUserAuthByUsername(String username) {
        return getOne(new LambdaQueryWrapper<UserAuth>()
                .eq(UserAuth::getUsername, username));
    }

    @Override
    public String login(UserVO user) {
        UserAuth userAuth = getOne(new LambdaQueryWrapper<UserAuth>().eq(UserAuth::getUsername, user.getUsername()));
        if (Objects.isNull(userAuth)) {
            throw new ServiceException(USERNAME_NOT_EXIST);
        }
        // 密码加密校验
        if (ShiroUtils.verifyPassword(user.getPassword(), userAuth.getPassword())) {
            String token = jwtProvider.createToken(userAuth.getUserInfoId());
            // 将token存入redis
            redisService.hSet(USER_TOKEN, String.valueOf(userAuth.getUserInfoId()), token, jwtProvider.getExpire());
            return token;
        }
        throw new ServiceException("账号或密码错误");
    }

    @Override
    public void logout() {
        redisService.hDel(USER_TOKEN, String.valueOf(ShiroUtils.getUserId(Integer.class)));
        SecurityUtils.getSubject().logout();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void register(UserVO user) {
        // 校验账号是否合法
        if (checkUser(user)) {
            throw new ServiceException("该邮箱已被注册");
        }
        // 新增用户信息
        UserInfo userInfo = UserInfo.builder()
                .email(user.getUsername())
                .nickname(DEFAULT_NICKNAME + IdWorker.getId())
                .avatar(websiteConfigService.getWebsiteConfig().getUserAvatar())
                .build();
        userInfoMapper.insert(userInfo);
        // 绑定用户角色
        UserRole userRole = UserRole.builder()
                .userId(userInfo.getId())
                .roleId(RoleEnum.ADMIN.getRoleId())
                .build();
        userRoleMapper.insert(userRole);
        // 新增用户账号
        UserAuth userAuth = UserAuth.builder()
                .userInfoId(userInfo.getId())
                .username(user.getUsername())
                .loginType(LoginTypeEnum.EMAIL.getType())
                .password(ShiroUtils.md5(user.getPassword()))
                .build();
        userAuthMapper.insert(userAuth);
    }

    /**
     * 校验用户数据是否合法
     *
     * @param user 用户数据
     * @return 结果
     */
    private Boolean checkUser(UserVO user) {
        //查询用户名是否存在
        UserAuth userAuth = userAuthMapper.selectOne(new LambdaQueryWrapper<UserAuth>()
                .select(UserAuth::getUsername)
                .eq(UserAuth::getUsername, user.getUsername()));
        return Objects.nonNull(userAuth);
    }
}




