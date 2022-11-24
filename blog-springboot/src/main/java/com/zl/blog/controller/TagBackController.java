package com.zl.blog.controller;

import com.zl.blog.common.Result;
import com.zl.blog.pojo.vo.ConditionVO;
import com.zl.blog.pojo.vo.TagVO;
import com.zl.blog.service.TagService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 标签控制器
 *
 * @author 冷血毒舌
 * @create 2022/11/19 14:59
 */
@Api(tags = "标签模块")
@RestController
@RequestMapping(value = "/admin/tag")
public class TagBackController {
    @Autowired
    private TagService tagService;

    /**
     * 查询标签列表
     *
     * @param conditionVO
     * @return
     */
    @ApiOperation(value = "查询标签列表")
    @GetMapping("/all")
    public Result listTagsBack(ConditionVO conditionVO) {
        return Result.success(tagService.listTagsBack(conditionVO));
    }


    /**
     * 搜索文章标签
     *
     * @param conditionVO
     * @return
     */
    @ApiOperation(value = "查询标签")
    @GetMapping("/search")
    public Result listTagBySearch(ConditionVO conditionVO) {
        return Result.success(tagService.listTagBySearch(conditionVO));
    }


    /**
     * 更新标签信息
     *
     * @param tagVO
     * @return
     */
    @ApiOperation(value = "更新标签信息")
    @PostMapping("/update")
    public Result saveOrUpdateTag(@RequestBody TagVO tagVO) {
        tagService.saveOrUpdateTag(tagVO);
        return Result.success();
    }

    /**
     * 删除标签
     *
     * @param tagIds
     * @return
     */
    @ApiOperation(value = "删除标签")
    @DeleteMapping("/delete")
    public Result deleteTags(@RequestBody List<Integer> tagIds) {
        tagService.deleteTags(tagIds);
        return Result.success();
    }
}
