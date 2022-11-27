package com.zl.blog.strategy;

import com.zl.blog.pojo.dto.ArticleSearchDTO;

import java.util.List;

/**
 * 搜索策略
 *
 * @author 冷血毒舌
 * @create 2022/11/26 11:20
 */
public interface SearchStrategy {

    /**
     * 根据关键字搜索文章
     *
     * @param keywords 关键字
     * @return {@link List<ArticleSearchDTO>}
     */
    List<ArticleSearchDTO> getArticles(String keywords);
}
