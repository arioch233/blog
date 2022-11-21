package com.zl.blog.controller;

import com.zl.blog.common.Result;
import com.zl.blog.service.BlogInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 博客信息模块
 *
 * @author 冷血毒舌
 * @create 2022/11/14 23:45
 */
@Api(tags = "博客信息模块")
@RestController
public class BlogInfoController {
    @Autowired
    private BlogInfoService blogInfoService;

    /**
     * 上传访客信息
     *
     * @return {@link Result}
     */
    @ApiOperation(value = "上传访客信息")
    @PostMapping("/report")
    public Result report() {
        blogInfoService.report();
        return Result.success();
    }


    /**
     * 查看博客后台信息
     *
     * @return
     */
    @ApiOperation(value = "查看后台信息")
    @GetMapping("/admin")
    public Result getBlogBackInfo() {
        return Result.success(blogInfoService.getBlogBackInfo());
    }

    /**
     * 查看博客信息
     *
     * @return
     */
    @ApiOperation(value = "查看博客信息")
    @GetMapping("/")
    public Result getBlogHomeInfo() {
        return Result.success(blogInfoService.getBlogHomeInfo());
    }
}
