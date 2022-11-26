package com.zl.blog.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zl.blog.common.PageResult;
import com.zl.blog.entity.Article;
import com.zl.blog.pojo.dto.*;
import com.zl.blog.pojo.vo.ArticleTopVO;
import com.zl.blog.pojo.vo.ArticleVO;
import com.zl.blog.pojo.vo.ConditionVO;
import com.zl.blog.pojo.vo.DeleteVO;

import java.util.List;

/**
 * 文章服务
 *
 * @author 冷血毒舌
 * @description 针对表【tb_article】的数据库操作Service
 * @createDate 2022-11-19 15:46:06
 */
public interface ArticleService extends IService<Article> {


    // 后台
    Page<ArticleBackDTO> listArticlesBack(ConditionVO conditionVO);

    ArticleVO getBackArticleById(Integer articleId);

    void saveOrUpdateArticle(ArticleVO articleVO);

    void updateArticleTopStatus(ArticleTopVO articleTopVO);

    void deleteArticleById(List<Integer> articleIds);

    void logicDeleteArticleById(DeleteVO deleteVO);

    // 前台
    List<ArticleHomeDTO> listArticles();

    ArticleDTO getArticleById(Integer articleId);

    List<ArticleSearchDTO> listArticlesBySearch(ConditionVO conditionVO);

    void saveArticleLike(Integer articleId);

    List<ArticleNewDTO> listArticlesNew();

    ArticlePreviewListDTO listArticlesByCondition(ConditionVO conditionVO);

    PageResult<ArchiveDTO> listArticleArchives();
}
