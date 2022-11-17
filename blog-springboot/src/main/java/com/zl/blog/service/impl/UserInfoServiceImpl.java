package com.zl.blog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zl.blog.entity.UserInfo;
import com.zl.blog.mapper.UserInfoMapper;
import com.zl.blog.service.UserInfoService;
import org.springframework.stereotype.Service;

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

}




