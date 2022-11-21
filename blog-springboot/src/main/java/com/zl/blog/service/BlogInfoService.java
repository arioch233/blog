package com.zl.blog.service;

import com.zl.blog.pojo.dto.BlogBackInfoDTO;
import com.zl.blog.pojo.dto.BlogHomeInfoDTO;

/**
 * 博客信息服务
 *
 * @author 冷血毒舌
 * @create 2022/11/14 23:45
 */
public interface BlogInfoService {
    /**
     * 上传访客信息
     */
    void report();

    /**
     * 获取博客后台信息
     *
     * @return
     */
    BlogBackInfoDTO getBlogBackInfo();

    /**
     * 获取博客信息
     *
     * @return
     */
    BlogHomeInfoDTO getBlogHomeInfo();
}
