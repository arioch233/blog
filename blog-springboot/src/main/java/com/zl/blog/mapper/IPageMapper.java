package com.zl.blog.mapper;

import com.zl.blog.entity.IPage;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zl.blog.pojo.dto.PageDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
* @author 冷血毒舌
* @description 针对表【tb_page(页面)】的数据库操作Service实现
* @createDate 2022-11-20 15:50:07
*/
@Mapper
public interface IPageMapper extends BaseMapper<IPage> {
    List<PageDTO> listPage();
}




