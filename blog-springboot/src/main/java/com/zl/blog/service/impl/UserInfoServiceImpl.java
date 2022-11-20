package com.zl.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zl.blog.entity.UserAuth;
import com.zl.blog.entity.UserInfo;
import com.zl.blog.entity.UserRole;
import com.zl.blog.exception.ServiceException;
import com.zl.blog.mapper.UserAuthMapper;
import com.zl.blog.mapper.UserInfoMapper;
import com.zl.blog.pojo.vo.PasswordVO;
import com.zl.blog.pojo.vo.UserDisableVO;
import com.zl.blog.pojo.vo.UserInfoVO;
import com.zl.blog.pojo.vo.UserRoleVO;
import com.zl.blog.service.RedisService;
import com.zl.blog.service.UserInfoService;
import com.zl.blog.service.UserRoleService;
import com.zl.blog.utils.ShiroUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.zl.blog.common.RedisPrefixConst.WEBSITE_NAME;

/**
 * 用户信息服务实现
 *
 * @author 冷血毒舌
 * @description 针对表【sys_user_info】的数据库操作Service实现
 * @createDate 2022-11-15 23:02:55
 */
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo>
        implements UserInfoService {

    @Resource
    private UserInfoMapper userInfoMapper;

    @Resource
    private UserAuthMapper userAuthMapper;

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private RedisService redisService;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateUserInfo(UserInfoVO userInfoVO) {
        // 封装用户信息
        UserInfo userInfo = UserInfo.builder()
                .id(ShiroUtils.getUserId(Integer.class))
                .nickname(userInfoVO.getNickname())
                .avatar(userInfoVO.getAvatar())
                .intro(userInfoVO.getIntro())
                .webSite(userInfoVO.getWebSite())
                .build();
        userInfoMapper.updateById(userInfo);
        // 刷新缓存
        redisService.hDel(WEBSITE_NAME);
    }

    @Override
    public void updateUserPassword(PasswordVO passwordVO) {
        // 查询旧密码是否正确
        UserAuth user = userAuthMapper.selectOne(new LambdaQueryWrapper<UserAuth>()
                .eq(UserAuth::getId, ShiroUtils.getUserId(Integer.class)));
        // 正确则修改密码，错误则提示不正确
        if (Objects.nonNull(user) && ShiroUtils.verifyPassword(passwordVO.getOldPassword(), user.getPassword())) {
            UserAuth userAuth = UserAuth.builder()
                    .id(ShiroUtils.getUserId(Integer.class))
                    .password(ShiroUtils.md5(passwordVO.getNewPassword()))
                    .build();
            userAuthMapper.updateById(userAuth);
        } else {
            throw new ServiceException("旧密码不正确");
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateUserDisable(UserDisableVO userDisableVO) {
        // 更新用户禁用状态
        UserInfo userInfo = UserInfo.builder()
                .id(userDisableVO.getId())
                .isDisable(userDisableVO.getIsDisable())
                .build();
        userInfoMapper.updateById(userInfo);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateUserRole(UserRoleVO userRoleVO) {
        // 更新用户角色和昵称
        UserInfo userInfo = UserInfo.builder()
                .id(userRoleVO.getUserInfoId())
                .nickname(userRoleVO.getNickname())
                .build();
        userInfoMapper.updateById(userInfo);
        // 删除用户角色重新添加
        userRoleService.remove(new LambdaQueryWrapper<UserRole>()
                .eq(UserRole::getUserId, userRoleVO.getUserInfoId()));
        List<UserRole> userRoleList = userRoleVO.getRoleIdList().stream()
                .map(roleId -> UserRole.builder()
                        .roleId(roleId)
                        .userId(userRoleVO.getUserInfoId())
                        .build())
                .collect(Collectors.toList());
        userRoleService.saveBatch(userRoleList);
    }
}




