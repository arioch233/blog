package com.zl.blog.controller;

import com.zl.blog.common.Result;
import com.zl.blog.pojo.vo.PageVO;
import com.zl.blog.service.IPageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 页面控制器
 *
 * @author 冷血毒舌
 * @create 2022/11/20 15:51
 */
@Api(tags = "页面模块")
@RestController
@RequestMapping(value = "/admin/page")
public class PageController {

    @Autowired
    private IPageService pagesService;

    /**
     * 获取页面列表
     *
     * @return {@link Result List<PageVO>}
     */
    @ApiOperation(value = "获取页面列表")
    @GetMapping("/all")
    public Result listPages() {
        return Result.success(pagesService.listPages());
    }


    /**
     * 保存或更新页面
     *
     * @param pageVO 页面信息
     * @return {@link Result}
     */
    @ApiOperation(value = "保存或更新页面")
    @PostMapping("/update")
    public Result saveOrUpdatePage(@Valid @RequestBody PageVO pageVO) {
        pagesService.saveOrUpdatePage(pageVO);
        return Result.success();
    }

    /**
     * 删除页面
     *
     * @param pageId 页面id
     * @return {@link Result}
     */
    @ApiOperation(value = "删除页面")
    @ApiImplicitParam(name = "pageId", value = "页面id", required = true, dataType = "Integer")
    @DeleteMapping("/delete/{pageId}")
    public Result deletePage(@PathVariable("pageId") Integer pageId) {
        pagesService.deletePage(pageId);
        return Result.success();
    }
}
