package com.zl.blog.controller;

import com.zl.blog.common.Result;
import com.zl.blog.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 分类控制器
 *
 * @author 冷血毒舌
 * @create 2022/11/24 22:49
 */
@Api(tags = "分类模块")
@RestController
@RequestMapping(value = "/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     * 分类列表
     *
     * @return
     */
    @ApiOperation("分类列表")
    @GetMapping("/list")
    public Result listCategory() {
        return Result.success(categoryService.listCategory());
    }
}
