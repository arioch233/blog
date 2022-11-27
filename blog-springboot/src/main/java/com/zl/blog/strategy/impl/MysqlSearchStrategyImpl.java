package com.zl.blog.strategy.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zl.blog.entity.Article;
import com.zl.blog.pojo.dto.ArticleSearchDTO;
import com.zl.blog.service.ArticleService;
import com.zl.blog.strategy.SearchStrategy;
import com.zl.blog.utils.BeanCopyUtils;
import com.zl.blog.utils.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.zl.blog.common.CommonConst.*;
import static com.zl.blog.common.enums.ArticleStatusEnum.PUBLIC;

/**
 * mysql 搜索策略
 *
 * @author 冷血毒舌
 * @create 2022/11/26 11:23
 */
@Slf4j
@Service("mysqlSearchStrategyImpl")
public class MysqlSearchStrategyImpl implements SearchStrategy {

    @Autowired
    private ArticleService articleService;

    @Override
    public List<ArticleSearchDTO> getArticles(String keywords) {
        log.error(keywords);
        // 判断关键字是否为空
        if (StringUtil.isEmpty(keywords)) {
            return new ArrayList<>();
        }
        // 查询数据库
        List<Article> articleList = articleService.list(new LambdaQueryWrapper<Article>()
                .select(Article::getId, Article::getArticleTitle, Article::getArticleContent)
                .eq(Article::getIsDelete, FALSE)
                .eq(Article::getStatus, PUBLIC.getStatus())
                .and(item -> item.like(Article::getArticleTitle, keywords)
                        .or()
                        .like(Article::getArticleContent, keywords)));
        // 文本高亮处理
        return articleList.stream().map(item -> {
            // 获取关键词第一次出现的位置
            String articleContent = item.getArticleContent();
            int index = item.getArticleContent().indexOf(keywords);
            if (index != -1) {
                // 获取关键词前面的文字
                int preIndex = index > 25 ? index - 25 : 0;
                String preText = item.getArticleContent().substring(preIndex, index);
                // 获取关键词到后面的文字
                int last = index + keywords.length();
                int postLength = item.getArticleContent().length() - last;
                int postIndex = postLength > 175 ? last + 175 : last + postLength;
                String postText = item.getArticleContent().substring(index, postIndex);
                // 文章内容高亮
                articleContent = (preText + postText).replaceAll(keywords, PRE_TAG + keywords + POST_TAG);
            }
            // 文章标题高亮
            String articleTitle = item.getArticleTitle().replaceAll(keywords, PRE_TAG + keywords + POST_TAG);
            return ArticleSearchDTO.builder()
                    .id(item.getId())
                    .articleTitle(articleTitle)
                    .articleContent(articleContent)
                    .build();
        }).collect(Collectors.toList());

    }

}
