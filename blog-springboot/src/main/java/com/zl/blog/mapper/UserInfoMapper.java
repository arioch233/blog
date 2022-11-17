package com.zl.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zl.blog.entity.UserInfo;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author 冷血毒舌
 * @description 针对表【sys_user_info】的数据库操作Service实现
 * @createDate 2022-11-15 23:02:55
 */
@Mapper
public interface UserInfoMapper extends BaseMapper<UserInfo> {

}




