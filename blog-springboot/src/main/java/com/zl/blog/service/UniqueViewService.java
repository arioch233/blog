package com.zl.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zl.blog.entity.UniqueView;
import com.zl.blog.pojo.dto.UniqueViewDTO;

import java.util.List;

/**
 * 访问量服务
 *
 * @author 冷血毒舌
 * @description 针对表【tb_unique_view】的数据库操作Service
 * @createDate 2022-11-21 15:18:18
 */
public interface UniqueViewService extends IService<UniqueView> {
    /**
     * 获取七天用户统计量
     *
     * @return 用户量
     */
    List<UniqueViewDTO> listUniqueView();
}
