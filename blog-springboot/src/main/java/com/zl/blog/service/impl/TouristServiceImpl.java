package com.zl.blog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zl.blog.entity.Tourist;
import com.zl.blog.mapper.TouristMapper;
import com.zl.blog.service.TouristService;
import org.springframework.stereotype.Service;

/**
 * 游客服务实现
 *
 * @author 冷血毒舌
 * @description 针对表【tb_tourist】的数据库操作Service实现
 * @createDate 2022-11-29 19:12:45
 */
@Service
public class TouristServiceImpl extends ServiceImpl<TouristMapper, Tourist>
        implements TouristService {

}




