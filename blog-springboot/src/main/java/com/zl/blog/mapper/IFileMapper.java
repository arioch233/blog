package com.zl.blog.mapper;

import com.zl.blog.entity.IFile;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
* @author 冷血毒舌
* @description 针对表【sys_file】的数据库操作Service实现
* @createDate 2022-11-13 00:36:49
*/
@Mapper
public interface IFileMapper extends BaseMapper<IFile> {

}




