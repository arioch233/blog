package com.zl.blog.mapper;

import com.zl.blog.entity.UserAuth;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author 冷血毒舌
* @description 针对表【sys_user_auth】的数据库操作Service实现
* @createDate 2022-11-15 23:02:46
*/
@Mapper
public interface UserAuthMapper extends BaseMapper<UserAuth> {

}




