package com.zl.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zl.blog.entity.Menu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 冷血毒舌
 * @description 针对表【sys_menu】的数据库操作Service实现
 * @createDate 2022-11-15 00:14:13
 */
public interface MenuMapper extends BaseMapper<Menu> {

    List<Menu> listMenusByUserInfoId(@Param("userInfoId") Integer userInfoId);
}




