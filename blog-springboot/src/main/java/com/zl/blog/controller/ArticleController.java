package com.zl.blog.controller;

import com.zl.blog.common.Result;
import com.zl.blog.pojo.vo.ConditionVO;
import com.zl.blog.service.ArticleService;
import com.zl.blog.strategy.context.SearchStrategyContext;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 博客首页控制器
 *
 * @author 冷血毒舌
 * @create 2022/11/24 15:32
 */
@Api(tags = "博客文章模块")
@RestController
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private SearchStrategyContext searchStrategyContext;

    /**
     * 获取首页文章列表
     *
     * @return
     */
    @ApiOperation("获取首页文章列表")
    @GetMapping("/list")
    public Result listArticles() {
        return Result.success(articleService.listArticles());
    }


    /**
     * 根据查询条件获取文章
     *
     * @param conditionVO
     * @return
     */
    @ApiOperation("根据查询条件获取文章")
    @GetMapping("/condition")
    public Result listArticlesByCondition(ConditionVO conditionVO) {
        return Result.success(articleService.listArticlesByCondition(conditionVO));
    }

    /**
     * 获取最新文章（3条）
     *
     * @return
     */
    @ApiOperation("获取最新文章列表")
    @GetMapping("/new")
    public Result listArticlesNew() {
        return Result.success(articleService.listArticlesNew());
    }

    /**
     * 搜索文章
     *
     * @param condition 条件
     * @return
     */
    @ApiOperation("文章搜索")
    @GetMapping("/search")
    public Result listArticlesBySearch(ConditionVO condition) {
//        return Result.success(articleService.listArticlesBySearch(condition));
        return Result.success(searchStrategyContext.executeSearchStrategy(condition.getKeywords()));
    }

    /**
     * 根据文章id获取文章信息
     *
     * @param articleId 文章id
     * @return
     */
    @ApiOperation("根据id获取文章信息")
    @GetMapping("/{articleId}")
    public Result getArticleById(@PathVariable Integer articleId) {
        return Result.success(articleService.getArticleById(articleId));
    }

    /**
     * 保存文章点赞信息
     *
     * @param articleId 文章id
     * @return
     */
    @ApiOperation("保存文章点赞信息")
    @PutMapping("/like/{articleId}")
    public Result saveArticleLike(@PathVariable Integer articleId) {
        articleService.saveArticleLike(articleId);
        return Result.success();
    }

    /**
     * 获取文章归档
     *
     * @return 归档信息
     */
    @ApiOperation("获取文章归档")
    @GetMapping("/archive")
    public Result listArticleArchives() {
        return Result.success(articleService.listArticleArchives());
    }
}
