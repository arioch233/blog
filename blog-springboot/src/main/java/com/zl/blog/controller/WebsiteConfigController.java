package com.zl.blog.controller;

import com.zl.blog.common.Result;
import com.zl.blog.pojo.vo.AboutMeVO;
import com.zl.blog.pojo.vo.WebsiteConfigVO;
import com.zl.blog.service.WebsiteConfigService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 网站配置信息控制器
 *
 * @author 冷血毒舌
 * @create 2022/11/19 16:49
 */
@Api(tags = "基本配置模块")
@RestController
@RequestMapping(value = "/admin/config")
public class WebsiteConfigController {
    @Autowired
    private WebsiteConfigService websiteConfigService;

    /**
     * 更新网站配置
     *
     * @param websiteConfigVO 网站配置信息
     * @return {@link Result}
     */
    @ApiOperation(value = "更新网站配置")
    @PutMapping("/website/update")
    public Result saveOrUpdateWebsiteConfig(@Valid @RequestBody WebsiteConfigVO websiteConfigVO) {
        websiteConfigService.saveOrUpdateWebsiteConfig(websiteConfigVO);
        return Result.success();
    }

    /**
     * 获取网站配置
     *
     * @return {@link Result<WebsiteConfigVO>} 网站配置
     */
    @ApiOperation(value = "获取网站配置")
    @GetMapping("/website")
    public Result getWebsiteConfig() {
        return Result.success(websiteConfigService.getWebsiteConfig());
    }

    /**
     * 查看关于我信息
     *
     * @return {@link Result<String>} 关于我信息
     */
    @ApiOperation(value = "查看关于我信息")
    @GetMapping("/about/info")
    public Result getAbout() {
        return Result.success(websiteConfigService.getAbout(), null);
    }

    /**
     * 修改关于我信息
     *
     * @param aboutMeVO 关于我
     * @return {@link Result}
     */
    @ApiOperation(value = "修改关于我信息")
    @PutMapping("/about/update")
    public Result saveOrUpdateAbout(@Valid @RequestBody AboutMeVO aboutMeVO) {
        websiteConfigService.saveOrUpdateAbout(aboutMeVO);
        return Result.success();
    }
}
