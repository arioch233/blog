package com.zl.blog.controller;

import com.zl.blog.common.Result;
import com.zl.blog.pojo.vo.CategoryVO;
import com.zl.blog.pojo.vo.ConditionVO;
import com.zl.blog.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 分类控制器
 *
 * @author 冷血毒舌
 * @create 2022/11/19 15:30
 */
@Api(tags = "分类模块")
@RestController
@RequestMapping(value = "/admin/category")
public class CategoryBackController {

    @Autowired
    private CategoryService categoryService;

    @ApiOperation(value = "获取分离列表")
    @GetMapping("/all")
    public Result listCategoryBack(ConditionVO conditionVO) {
        return Result.success(categoryService.listCategoryBack(conditionVO));
    }

    @ApiOperation(value = "获取分类")
    @GetMapping("/search")
    public Result listCategoryBySearch(ConditionVO conditionVO) {
        return Result.success(categoryService.listCategoryBySearch(conditionVO));
    }

    @ApiOperation(value = "更新分类信息")
    @PostMapping("/update")
    public Result saveOrUpdateCategory(@RequestBody CategoryVO categoryVO) {
        categoryService.saveOrUpdateCategory(categoryVO);
        return Result.success();
    }

    @ApiOperation(value = "删除分类")
    @DeleteMapping("/delete")
    public Result deleteCategory(@RequestBody List<Integer> categoryIds) {
        categoryService.deleteCategory(categoryIds);
        return Result.success();
    }

}
