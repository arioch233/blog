package com.zl.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zl.blog.entity.Article;
import com.zl.blog.pojo.dto.*;
import com.zl.blog.pojo.vo.ConditionVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 冷血毒舌
 * @description 针对表【tb_article】的数据库操作Service实现
 * @createDate 2022-11-19 15:46:06
 */
@Mapper
public interface ArticleMapper extends BaseMapper<Article> {
    /**
     * 查询后台文章
     *
     * @param condition 条件
     * @return 文章列表
     */
    Page<ArticleBackDTO> listArticlesBack(Page<ArticleBackDTO> page, @Param("condition") ConditionVO condition);

    /**
     * 文章统计
     *
     * @return
     */
    List<ArticleStatisticsDTO> listArticleStatistics();


    List<ArticleRecommendDTO> listRecommendArticles(Integer articleId);

    /**
     * 查询首页文章
     *
     * @param current 页码
     * @param size    大小
     * @return 文章列表
     */
    List<ArticleHomeDTO> listArticles(@Param("current") Long current, @Param("size") Long size);

    /**
     * 根据id查询文章
     *
     * @param articleId 文章id
     * @return 文章信息
     */
    ArticleDTO getArticleById(@Param("articleId") Integer articleId);

    /**
     * 获取最新文章列表
     *
     * @return
     */
    List<ArticleNewDTO> listArticlesNew();

    /**
     * 根据查询条件搜索文章
     *
     * @param current     页码
     * @param size        大小
     * @param conditionVO 条件
     * @return 预览文章列表
     */
    List<ArticlePreviewDTO> listArticlesByCondition(@Param("current") Long current, @Param("size") Long size, @Param("condition") ConditionVO conditionVO);
}




