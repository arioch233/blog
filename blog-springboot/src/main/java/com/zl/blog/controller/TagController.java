package com.zl.blog.controller;

import com.zl.blog.common.Result;
import com.zl.blog.service.TagService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 标签控制器
 *
 * @author 冷血毒舌
 * @create 2022/11/24 22:50
 */
@Api(tags = "标签模块")
@RestController
@RequestMapping(value = "/tag")
public class TagController {

    @Autowired
    private TagService tagService;

    /**
     * 获取标签列表
     *
     * @return
     */
    @ApiOperation("获取标签列表")
    @GetMapping("/list")
    public Result listTags() {
        return Result.success(tagService.listTags());
    }
}
