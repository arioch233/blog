package com.zl.blog.mapper;

import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zl.blog.entity.UniqueView;
import com.zl.blog.pojo.dto.UniqueViewDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 冷血毒舌
 * @description 针对表【tb_unique_view】的数据库操作Service实现
 * @createDate 2022-11-21 15:18:18
 */
@Mapper
public interface UniqueViewMapper extends BaseMapper<UniqueView> {

    /**
     * 七天用户访问统计
     *
     * @param startTime
     * @param endTime
     * @return
     */
    List<UniqueViewDTO> listUniqueView(@Param("startTime") DateTime startTime, @Param("endTime") DateTime endTime);
}




