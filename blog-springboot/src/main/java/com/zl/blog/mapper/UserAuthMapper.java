package com.zl.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zl.blog.entity.UserAuth;
import com.zl.blog.pojo.dto.UserBackDTO;
import com.zl.blog.pojo.vo.ConditionVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 冷血毒舌
 * @description 针对表【sys_user_auth】的数据库操作Service实现
 * @createDate 2022-11-15 23:02:46
 */
@Mapper
public interface UserAuthMapper extends BaseMapper<UserAuth> {

    Long userCount(@Param("condition") ConditionVO condition);

    List<UserBackDTO> listUsers(@Param("current") Long current, @Param("size") Long size, @Param("condition") ConditionVO condition);


}




