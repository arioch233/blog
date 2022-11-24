package com.zl.blog.controller;

import com.zl.blog.common.Result;
import com.zl.blog.pojo.vo.ArticleTopVO;
import com.zl.blog.pojo.vo.ArticleVO;
import com.zl.blog.pojo.vo.ConditionVO;
import com.zl.blog.pojo.vo.DeleteVO;
import com.zl.blog.service.ArticleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 文章控制器
 *
 * @author 冷血毒舌
 * @create 2022/11/19 16:00
 */
@Api(tags = "后台文章管理模块")
@RestController
@RequestMapping(value = "/admin/article")
public class ArticleBackController {
    @Autowired
    private ArticleService articleService;

    /**
     * 获取所有的文章
     *
     * @param conditionVO
     * @return
     */
    @ApiOperation(value = "获取所有的文章列表")
    @GetMapping("/all")
    public Result listArticlesBack(ConditionVO conditionVO) {
        return Result.success(articleService.listArticlesBack(conditionVO));
    }

    /**
     * 后台根据文章id获取文章相关信息
     *
     * @param articleId
     * @return
     */
    @ApiOperation(value = "获取文章")
    @GetMapping("/{articleId}")
    public Result getBackArticle(@PathVariable Integer articleId) {
        return Result.success(articleService.getBackArticleById(articleId));
    }

    /**
     * 保存和更新文章
     *
     * @param articleVO
     * @return
     */
    @ApiOperation(value = "更新文章信息")
    @PostMapping("/update")
    public Result saveOrUpdateArticle(@RequestBody ArticleVO articleVO) {
        articleService.saveOrUpdateArticle(articleVO);
        return Result.success();
    }

    /**
     * 更新文章置顶状态
     *
     * @param articleTopVO
     * @return
     */
    @ApiOperation(value = "更新文章是否置顶状态")
    @PutMapping("/top/update")
    public Result updateArticleTopStatus(@Valid @RequestBody ArticleTopVO articleTopVO) {
        articleService.updateArticleTopStatus(articleTopVO);
        return Result.success();
    }

    /**
     * 删除文章 删除
     *
     * @param articleIds
     * @return
     */
    @ApiOperation(value = "删除文章")
    @DeleteMapping("/delete")
    public Result deleteArticle(@RequestBody List<Integer> articleIds) {
        articleService.deleteArticleById(articleIds);
        return Result.success();
    }

    /**
     * 根据id逻辑删除文章
     *
     * @param deleteVO
     * @return
     */
    @ApiOperation(value = "根据id逻辑删除文章")
    @PutMapping("/delete/logic")
    public Result logicDeleteArticleById(@Valid @RequestBody DeleteVO deleteVO) {
        articleService.logicDeleteArticleById(deleteVO);
        return Result.success();
    }
}
